package com.gilboot.easypark.park

import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.gilboot.easypark.Repository
import com.gilboot.easypark.model.Park

class ParkViewModel(val parkId: String, val repository: Repository) : ViewModel() {
    val parkLiveData = repository.getParkById(parkId)


}