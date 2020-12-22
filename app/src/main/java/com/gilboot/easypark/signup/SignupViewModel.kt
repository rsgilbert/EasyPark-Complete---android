package com.gilboot.easypark.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.gilboot.easypark.model.UserType


// ViewModel for SignupFragment
class SignupViewModel : ViewModel() {
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

