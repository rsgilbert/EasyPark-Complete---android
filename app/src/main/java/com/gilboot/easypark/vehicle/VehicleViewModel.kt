package com.gilboot.easypark.vehicle

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gilboot.easypark.data.Vehicle


// ViewModel with vehicle liveData
class VehicleViewModel : ViewModel() {
    val vehicleLiveData = MutableLiveData<Vehicle>()
}


