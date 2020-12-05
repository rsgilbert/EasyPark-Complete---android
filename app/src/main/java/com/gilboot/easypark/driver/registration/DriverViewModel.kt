package com.gilboot.easypark.driver.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gilboot.easypark.data.Driver
import com.gilboot.easypark.data.Vehicle

class DriverViewModel : ViewModel() {

    val driverLiveData = MutableLiveData<Driver>().apply { value = Driver() }

    val vehicleLiveData = MutableLiveData<Vehicle>().apply { value = Vehicle() }

}

