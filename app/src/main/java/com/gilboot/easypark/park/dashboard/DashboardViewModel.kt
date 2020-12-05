package com.gilboot.easypark.park.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gilboot.easypark.data.Visit


// ViewModel for DashboardFragment
class DashboardViewModel : ViewModel() {
    val visitsLiveData = MutableLiveData<List<Visit>>()


}