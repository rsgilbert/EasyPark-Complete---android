package com.gilboot.easypark.parksignup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Database
import com.gilboot.easypark.Repository
import com.gilboot.easypark.model.Park
import com.gilboot.easypark.util.uploadPicture
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.InputStream


class ParkinfoViewModel(val repository: Repository) : ViewModel() {


    val emailLiveData = MutableLiveData<String>()
    val passwordLiveData = MutableLiveData<String>()
    val nameLiveData = MutableLiveData<String>()
    val pictureLiveData = MutableLiveData<String>()
    val telephoneLiveData = MutableLiveData<String>()
    val capacityLiveData = MutableLiveData<Int>()
    val latLngLiveData = MutableLiveData<LatLng>()

    val navigateToDashboardLiveData = MutableLiveData<Unit>()


}

fun ParkinfoViewModel.setPicture(stream: InputStream) {
    uploadPicture(stream, { pictureUrl ->
        pictureLiveData.value = pictureUrl
    }, { err ->
        Timber.e("Failed to upload picture, $err")
    })
}


fun ParkinfoViewModel.setName(name: String) {
    nameLiveData.value = name
}

fun ParkinfoViewModel.setCapacity(capacity: Int) {
    capacityLiveData.value = capacity
}

fun ParkinfoViewModel.setEmail(email: String) {
    emailLiveData.value = email
}

fun ParkinfoViewModel.setPassword(password: String) {
    passwordLiveData.value = password
}

fun ParkinfoViewModel.setTelephone(telephone: String) {
    telephoneLiveData.value = telephone
}

fun ParkinfoViewModel.setLatLng(latLng: com.google.android.gms.maps.model.LatLng) {
    latLngLiveData.value = latLng
}

fun ParkinfoViewModel.navigateToDashboard() {
    navigateToDashboardLiveData.value = Unit
}

fun ParkinfoViewModel.navigateToDashboardComplete() {
    navigateToDashboardLiveData.value = null
}

fun ParkinfoViewModel.signup() {
    viewModelScope.launch {
        val isSuccess: Boolean? = repository.signupPark(
            emailLiveData.value!!,
            passwordLiveData.value!!,
            nameLiveData.value!!,
            telephoneLiveData.value!!,
            latLngLiveData.value!!.latitude,
            latLngLiveData.value!!.longitude,
            pictureLiveData.value!!
        )
        isSuccess?.let {
            navigateToDashboard()
        }
    }
}