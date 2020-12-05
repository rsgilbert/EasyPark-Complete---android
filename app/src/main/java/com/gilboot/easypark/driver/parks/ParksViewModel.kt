package com.gilboot.easypark.driver.parks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gilboot.easypark.data.Park


// holds list of nearby parks to show to the driver
class ParksViewModel : ViewModel() {

    val parksLiveData = MutableLiveData<List<Park>>()


}