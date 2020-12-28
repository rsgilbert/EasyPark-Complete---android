package com.gilboot.easypark.choose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.gilboot.easypark.Repository
import com.gilboot.easypark.model.UserType
import timber.log.Timber

class ChooseViewModel(val repository: Repository) : ViewModel() {

    val userTypeLiveData: LiveData<UserType> = liveData {
        emit(repository.getUserType())
    }


}