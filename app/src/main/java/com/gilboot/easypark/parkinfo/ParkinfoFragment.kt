package com.gilboot.easypark.parkinfo

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.gilboot.easypark.R
import com.gilboot.easypark.data.Park
import com.gilboot.easypark.databinding.FragParkinfoBinding
import com.gilboot.easypark.util.saveUserToPrefs
import com.gilboot.easypark.util.withAuthParkSignup
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.support.v4.toast
import timber.log.Timber


class ParkinfoFragment : Fragment() {

    lateinit var binding: FragParkinfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.frag_parkinfo,
                container,
                false
            )

        binding.buttonSignup.setOnClickListener { attemptSignup() }

        return binding.root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults.first() != PackageManager.PERMISSION_GRANTED) {
                    longToast("Unable to show current location, permission required")
                } else {
                    onRequestPermissionsGranted()
                }
            }
        }
    }

}

val ParkinfoFragment.parkInArgs: Park
    get() = ParkinfoFragmentArgs.fromBundle(arguments!!).park

fun ParkinfoFragment.attemptSignup() {
    withCurrentLocation { latLng ->
        val park = parkInArgs.copy(
            name = binding.editParkname.text.toString(),
            tel = binding.editTel.text.toString(),
            lat = latLng.latitude,
            lng = latLng.longitude
        )
        toast("latLng is ${latLng.latitude}, ${latLng.longitude}")
        withAuthParkSignup(park) {
            when (it) {
                null -> toast("Failed to signup")
                else -> {
                    toast(it.toString())
                    requireContext().saveUserToPrefs(it.user)
                    navigateToDashboard()
                }
            }
        }
    }

}

fun ParkinfoFragment.navigateToDashboard() {
    findNavController().navigate(ParkinfoFragmentDirections.actionParkinfoFragmentToDashboardFragment())
}


fun Fragment.requestLocationPermissions() =
    ActivityCompat.requestPermissions(
        requireActivity(),
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
        LOCATION_REQUEST_CODE
    )

fun Fragment.getFusedLocationProviderClient(): FusedLocationProviderClient =
    getFusedLocationProviderClient(requireActivity())


fun Fragment.isLocationPermissionsEnabled() =
    ContextCompat.checkSelfPermission(
        context!!, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

fun Fragment.withLocationPermissionsEnabled(lambda: () -> Unit) {
    if (isLocationPermissionsEnabled()) {
        lambda()
    } else {
        requestLocationPermissions()
    }
}

fun Fragment.withCurrentLocation(lambda: (latLng: LatLng) -> Unit) {
    try {
        if (isLocationPermissionsEnabled()) {
            val locationResult: Task<Location> =
                getFusedLocationProviderClient().lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val location: Location? = task.result
                    location?.let {
                        val currentLatLng = LatLng(
                            it.latitude, it.longitude
                        )
                        lambda(currentLatLng)
                    }
                }
            }
        } else requestLocationPermissions()
    } catch (e: SecurityException) {
        toast("Failed to get current location")
        Timber.e("Exception: %s", e.message)
    }
}


fun ParkinfoFragment.onRequestPermissionsGranted() {
    attemptSignup()
}

// Constants
const val LOCATION_REQUEST_CODE = 101
const val padding = 50
