package com.gilboot.easypark.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.gilboot.easypark.data.UserType


// ViewModel for SignupFragment
class LoginViewModel : ViewModel() {
    val userType = MutableLiveData<UserType>().apply { value = UserType.Driver }

    val isParkLiveData = userType.map {
        it == UserType.Park
    }

    fun setToDriver() {
        userType.value = UserType.Driver
    }

    fun setToPark() {
        userType.value = UserType.Park
    }


}

