package com.gilboot.easypark.driversignup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilboot.easypark.Event
import com.gilboot.easypark.Repository
import com.gilboot.easypark.parklogin.ParkloginViewModel
import com.gilboot.easypark.parklogin.setSnackMessage
import kotlinx.coroutines.launch

class DriversignupViewModel(val repository: Repository) : ViewModel() {
    val navigateToParksLiveData = MutableLiveData<Unit>()
    val snackMessageLiveData = MutableLiveData<Event<String>>()

}

fun DriversignupViewModel.navigateToParks() {
    navigateToParksLiveData.value = Unit
}

fun DriversignupViewModel.navigateToParksComplete() {
    navigateToParksLiveData.value = null
}

fun DriversignupViewModel.setSnackMessage(message: String) {
    snackMessageLiveData.value = Event(message)
}

fun DriversignupViewModel.signupDriver(email: String, password: String) {
    viewModelScope.launch {
        val isSuccess = repository.signupDriver(email, password)
        isSuccess?.let {
            navigateToParks()
            setSnackMessage("Successfully signed up")
        }
        if (isSuccess == null)
            setSnackMessage("Failed to sign up")
    }
}