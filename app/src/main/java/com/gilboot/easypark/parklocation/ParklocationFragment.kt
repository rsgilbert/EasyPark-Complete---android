package com.gilboot.easypark.parklocation

import androidx.fragment.app.Fragment
import com.gilboot.easypark.*
import com.google.android.gms.maps.model.LatLng

class ParklocationFragment : MapFragment() {
    override fun whenMapIsReady() {
        super.whenMapIsReady()
        val parkLatLng = LatLng(parkFromArgs.lat, parkFromArgs.lng)
        setCamera(parkLatLng)
        addGreenMarker(parkLatLng, parkFromArgs.name, parkFromArgs.tel)


    }
}

val ParklocationFragment.parkFromArgs
get() = ParklocationFragmentArgs.fromBundle(arguments!!).park