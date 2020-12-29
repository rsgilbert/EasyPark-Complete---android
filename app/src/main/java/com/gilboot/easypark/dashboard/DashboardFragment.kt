package com.gilboot.easypark.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.gilboot.easypark.R
import com.gilboot.easypark.databinding.FragDashboardBinding
import com.gilboot.easypark.setCorrectDrawerMenu
import com.gilboot.easypark.util.repository
import com.google.zxing.client.android.Intents
import com.google.zxing.integration.android.IntentIntegrator
import org.jetbrains.anko.support.v4.longToast
import timber.log.Timber


// Dashboard where the park records new vehicles
// that have visited the park
// The park also removes vehicles that have left
class DashboardFragment : Fragment() {
    private val dashboardViewModel: DashboardViewModel by activityViewModels {
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

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.scan -> scanQr()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (!arrayOf(SCAN_REQUEST_CODE, IntentIntegrator.REQUEST_CODE).contains(requestCode)) {
            super.onActivityResult(requestCode, resultCode, data)
            return
        }
        val result = IntentIntegrator.parseActivityResult(resultCode, data)
        when (result.contents) {
            null -> {
                when {
                    result.originalIntent == null -> longToast("Cancelled")
                    result.originalIntent.hasExtra(Intents.Scan.MISSING_CAMERA_PERMISSION) -> longToast(
                        "Cancelled"
                    )
                }
            }
            else -> {
                longToast("Scanned: ${result.contents}")
                Timber.i("Results:")
                Timber.i(result.contents)
            }
        }
    }
}

fun DashboardFragment.scanQr() {
    val integrator = IntentIntegrator(requireActivity())
    integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
    integrator.initiateScan()
}

const val SCAN_REQUEST_CODE = 1

