package com.gilboot.easypark

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.Task
import com.google.maps.android.PolyUtil
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.support.v4.toast
import timber.log.Timber


open class MapFragment : Fragment(), OnMapReadyCallback {
    var map: GoogleMap? = null
    private var mIsRestore = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayout(), container, false)
        mIsRestore = savedInstanceState != null
        setupMap()
        // We need to setHasOptionsMenu(true) else our options menu wont show in
        // fragments that extend MapFragment. Probably because MapFragment
        // is not part of the navigation ui so needs this option to be set explicitly
        setHasOptionsMenu(true)
        moreOnCreateView(view)
        return view

    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap
//        setInfoWindow()
        map?.let {
            withLocationPermissionsEnabled {
                it.isMyLocationEnabled = true
                it.uiSettings.isMyLocationButtonEnabled = true
//            } else {
//                requestLocationPermissions()
            }
            whenMapIsReady()
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
                    setupMap()
                }
            }
            REQUEST_AUDIO_PERMISSION_CODE -> if (grantResults.isNotEmpty()) {
                val permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED
                if (permissionToRecord) {
                    toast("Permission granted")
                    onRequestPermissionsGranted()
                }
            }
        }
    }

    open fun onRequestPermissionsGranted() {}

    open fun getLayout() = R.layout.fragment_map

    open fun moreOnCreateView(view: View) {}

    open fun whenMapIsReady() {

    }
}


fun MapFragment.addPolyline(line: String) {
    val decodedPath: List<LatLng> = PolyUtil.decode(line)
    getMap()?.addPolyline(PolylineOptions().addAll(decodedPath))
}

fun MapFragment.setCamera(latLng: LatLng) {
    getMap()?.moveCamera(
        CameraUpdateFactory.newLatLngZoom(
            latLng, zoom
        )
    )
}

fun MapFragment.setCameraAroundBounds(
    neBoundLatitude: Double, neBoundLongitude: Double,
    swBoundLatitude: Double, swBoundLongitude: Double
) {
    val neBoundLatLng = LatLng(neBoundLatitude, neBoundLongitude)
    val swBoundLatLng = LatLng(swBoundLatitude, swBoundLongitude)
    val latLngBounds = LatLngBounds(swBoundLatLng, neBoundLatLng)
    getMap()?.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, padding))
}


fun MapFragment.clearMarkers() {
    getMap()?.clear()
}

fun MapFragment.addGreenMarker(latLng: LatLng, title: String = "", snippet: String = "") {
    getMap()?.apply {
        addMarker(
            MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .position(latLng)
                .title(title)
                .snippet(snippet)
        )
    }
}

fun MapFragment.addRedMarker(latLng: LatLng, snippet: String) {
    val title = "End"
    getMap()?.apply {
        addMarker(
            MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
                .position(latLng)
                .title(title)
                .snippet(snippet)
        )
    }
}


fun MapFragment.getFusedLocationProviderClient(): FusedLocationProviderClient =
    LocationServices.getFusedLocationProviderClient(requireActivity())

fun MapFragment.getMap() = map

fun MapFragment.withCurrentLocation(lambda: (latLng: LatLng) -> Unit) {
    try {
        if (isLocationPermissionsEnabled()) {
            val locationResult: Task<Location> =
                getFusedLocationProviderClient().lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Set the map's camera position to the current location of the device.
                    val location: Location? = task.result
                    location?.let {
                        val currentLatLng = LatLng(
                            it.latitude, it.longitude
                        )
                        lambda(currentLatLng)
                    }
                }
            }
        }
    } catch (e: SecurityException) {
        toast("Failed to get current location")
        Timber.e("Exception: %s", e.message)
    }
}

fun MapFragment.moveCameraToCurrentLocation() {
    withCurrentLocation { setCamera(it) }
}


fun MapFragment.setupMap() =
    (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)!!.getMapAsync(this)

fun MapFragment.isLocationPermissionsEnabled() =
    ContextCompat.checkSelfPermission(
        context!!, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

fun MapFragment.withLocationPermissionsEnabled(lambda: () -> Unit) {
    if (isLocationPermissionsEnabled()) {
        lambda()
    } else {
        requestLocationPermissions()
    }
}

fun MapFragment.requestLocationPermissions() =
    ActivityCompat.requestPermissions(
        requireActivity(),
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
        LOCATION_REQUEST_CODE
    )


// Constants
const val zoom = 20F
const val LOCATION_REQUEST_CODE = 101
const val padding = 50

