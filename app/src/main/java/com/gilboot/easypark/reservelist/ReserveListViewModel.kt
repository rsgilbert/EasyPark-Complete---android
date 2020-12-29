package com.gilboot.easypark.reservelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilboot.easypark.Repository
import com.gilboot.easypark.model.Visit
import kotlinx.coroutines.launch
import timber.log.Timber


// holds list of nearby parks to show to the driver
class ReserveListViewModel(val repository: Repository) : ViewModel() {

    val reserveListLiveData: LiveData<List<Visit>> = repository.getReserveList()

    init {
        updateVisits()

    }

    private fun updateVisits() {
        viewModelScope.launch {
            repository.updateVisits()
        }
    }

}


