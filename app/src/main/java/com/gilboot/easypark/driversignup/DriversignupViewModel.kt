package com.gilboot.easypark.driversignup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilboot.easypark.Repository
import kotlinx.coroutines.launch

class DriversignupViewModel(val repository: Repository) : ViewModel() {
    val navigateToParksLiveData = MutableLiveData<Unit>()

}

fun DriversignupViewModel.navigateToParks() {
    navigateToParksLiveData.value = Unit
}

fun DriversignupViewModel.navigateToParksComplete() {
    navigateToParksLiveData.value = null
}


fun DriversignupViewModel.signupDriver(email: String, password: String) {
    viewModelScope.launch {
        val isSuccess = repository.signupDriver(email, password)
        isSuccess?.let {
            navigateToParks()
        }
    }
}