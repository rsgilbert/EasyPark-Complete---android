package com.gilboot.easypark.parksignup

import android.Manifest
import android.app.Activity
import android.content.Intent
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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gilboot.easypark.PICK_PHOTO_REQUEST_CODE
import com.gilboot.easypark.R
import com.gilboot.easypark.databinding.FragParkinfoBinding
import com.gilboot.easypark.util.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.support.v4.toast
import timber.log.Timber
import java.io.InputStream
import androidx.lifecycle.observe


class ParkinfoFragment : Fragment() {
    val parkinfoViewModel: ParkinfoViewModel by activityViewModels {
        ParkinfoViewModelFactory(
            repository
        )
    }
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

        binding.parkinfoViewModel = parkinfoViewModel
        binding.apply {
            lifecycleOwner = viewLifecycleOwner

        }

        parkinfoViewModel.navigateToDashboardLiveData.observe(
            viewLifecycleOwner,
            Observer { navigate ->
                navigate?.let {
                    navigateToDashboard()
                    parkinfoViewModel.navigateToDashboardComplete()
                }
            })

        binding.picture.setOnClickListener { startImagePicker() }
        binding.buttonSignup.setOnClickListener { attemptSignup() }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            try {
                data?.let { intent ->
                    val inputStream: InputStream? =
                        context?.contentResolver?.openInputStream(intent.data!!)
                    inputStream?.let { stream ->
                        if (!requireContext().isNetworkAvailable()) {
                            longToast("Network error")
                        } else {
                            toast("Adding picture")
                            parkinfoViewModel.setPicture(stream)

                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
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


fun ParkinfoFragment.attemptSignup() {
    withCurrentLocation { latLng ->
        parkinfoViewModel.apply {
            setLatLng(latLng)
            binding.apply {
                when {
                    !editParkname.isValidInput() -> toast("Park name can not be blank")
                    !editCapacity.isValidInput() -> toast("Capacity can not be blank")
                    pictureLiveData.value.isNullOrBlank() -> toast("Picture is not set")
                    else -> {
                        setName(editParkname.textValue())
                        setCapacity(editCapacity.textValue().toInt())
                        signup()
                    }
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

fun ParkinfoFragment.startImagePicker() {
    val intent = Intent(Intent.ACTION_GET_CONTENT)
    intent.type = "image/*"
    startActivityForResult(intent, PICK_PHOTO_REQUEST_CODE)
}


// Constants
const val LOCATION_REQUEST_CODE = 101
const val padding = 50
