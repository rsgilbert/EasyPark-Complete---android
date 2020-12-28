package com.gilboot.easypark.driverlogin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilboot.easypark.Event
import com.gilboot.easypark.Repository
import com.gilboot.easypark.parklogin.ParkloginViewModel
import com.gilboot.easypark.parklogin.setSnackMessage
import kotlinx.coroutines.launch

class DriverloginViewModel(val repository: Repository) : ViewModel() {
    val navigateToParksLiveData = MutableLiveData<Unit>()
    val snackMessageLiveData = MutableLiveData<Event<String>>()

}

fun DriverloginViewModel.navigateToParks() {
    navigateToParksLiveData.value = Unit
}

fun DriverloginViewModel.navigateToParksComplete() {
    navigateToParksLiveData.value = null
}

fun DriverloginViewModel.setSnackMessage(message: String) {
    snackMessageLiveData.value = Event(message)
}

fun DriverloginViewModel.loginDriver(email: String, password: String) {
    viewModelScope.launch {
        val isSuccess = repository.loginDriver(email, password)
        isSuccess?.let {
            navigateToParks()
            setSnackMessage("Successfully logged in")
        }
        if (isSuccess == null)
            setSnackMessage("Failed to log in")
    }
}