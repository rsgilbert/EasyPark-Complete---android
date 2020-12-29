package com.gilboot.easypark.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.gilboot.easypark.R
import com.gilboot.easypark.SCAN_ARRIVAL_REQUEST_CODE
import com.gilboot.easypark.SCAN_DEPARTURE_REQUEST_CODE
import com.gilboot.easypark.databinding.FragDashboardBinding
import com.gilboot.easypark.dialogs.ScanDialog
import com.gilboot.easypark.dialogs.ScanListener
import com.gilboot.easypark.setCorrectDrawerMenu
import com.gilboot.easypark.util.indefiniteSnackbar
import com.gilboot.easypark.util.repository
import com.google.zxing.client.android.Intents
import com.google.zxing.integration.android.IntentIntegrator
import org.jetbrains.anko.support.v4.longToast
import timber.log.Timber

// Dashboard where the park records new vehicles
// that have visited the park
// The park also removes vehicles that have left
class DashboardFragment : Fragment(), ScanListener {
    val dashboardViewModel: DashboardViewModel by activityViewModels {
        DashboardViewModelFactory(repository)
    }

    lateinit var binding: FragDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        setCorrectDrawerMenu()
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.frag_dashboard,
                container,
                false
            )

        binding.dashboardViewModel = dashboardViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        observeSnackMessage()

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.scan -> showScanDialog()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (!arrayOf(
                SCAN_ARRIVAL_REQUEST_CODE,
                SCAN_DEPARTURE_REQUEST_CODE,
                IntentIntegrator.REQUEST_CODE
            ).contains(requestCode)
        ) {
            super.onActivityResult(requestCode, resultCode, data)
            return
        }

        val result = IntentIntegrator.parseActivityResult(resultCode, data)
        when (result.contents) {
            null -> {
                when {
                    result.originalIntent == null -> longToast("Cancelled")
                    result.originalIntent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION) -> longToast(
                        "Cancelled due to missing camera permissions"
                    )
                }
            }
            else -> {
                when (requestCode) {
                    SCAN_ARRIVAL_REQUEST_CODE -> processArrival(result.contents)
                    SCAN_DEPARTURE_REQUEST_CODE -> processDeparture(result.contents)
                }
                Timber.i(result.contents)
            }
        }
    }

    override fun scanArrival() {
        scanQr(SCAN_ARRIVAL_REQUEST_CODE)
    }

    override fun scanDeparture() {
        scanQr(SCAN_DEPARTURE_REQUEST_CODE)
    }
}

fun DashboardFragment.processArrival(visitId: String) {
    dashboardViewModel.processArrival(visitId)
}

fun DashboardFragment.processDeparture(visitId: String) {
    dashboardViewModel.processDeparture(visitId)
}


/**
 * Performs a qr code scan using zxing-android-embedded library
 */
fun DashboardFragment.scanQr(requestCode: Int) {
    IntentIntegrator.forSupportFragment(this)
        .setRequestCode(requestCode)
        .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        .setOrientationLocked(false)
        .initiateScan()
}


/**
 * Displays a scan dialog where the user picks which kind of scan to take
 */
private fun DashboardFragment.showScanDialog() {
    val dialogFragment = ScanDialog()
    dialogFragment.show(childFragmentManager, "My Scan Dialog Fragment")
}


/**
 * Observe snack message
 */
fun DashboardFragment.observeSnackMessage() {
    dashboardViewModel.snackMessageLiveData.observe(viewLifecycleOwner, Observer {
        it?.let { event ->
            event.getContentIfNotHandledOrReturnNull()?.let { msg ->
                indefiniteSnackbar(msg)
            }
        }
    })
}



