package com.gilboot.easypark.parklogin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilboot.easypark.Repository
import com.gilboot.easypark.parksignup.ParkinfoViewModel
import kotlinx.coroutines.launch

class ParkloginViewModel(val repository: Repository) : ViewModel() {
    val navigateToDashboardLiveData = MutableLiveData<Unit>()

}

fun ParkloginViewModel.navigateToDashboard() {
    navigateToDashboardLiveData.value = Unit
}

fun ParkloginViewModel.navigateToDashboardComplete() {
    navigateToDashboardLiveData.value = null
}


fun ParkloginViewModel.loginPark(email: String, password: String) {
    viewModelScope.launch {
        val isSuccess = repository.loginPark(email, password)
        isSuccess?.let {
            navigateToDashboard()
        }
    }

}