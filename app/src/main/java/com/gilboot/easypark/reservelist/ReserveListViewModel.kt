package com.gilboot.easypark.reservelist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilboot.easypark.Repository
import kotlinx.coroutines.launch
import timber.log.Timber


// holds list of nearby parks to show to the driver
class ReserveListViewModel(val repository: Repository) : ViewModel() {

    val reserveListLiveData = repository.getReserveList()


}


