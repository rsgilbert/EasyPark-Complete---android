package com.gilboot.easypark.parks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilboot.easypark.Repository
import kotlinx.coroutines.launch
import timber.log.Timber


// holds list of nearby parks to show to the driver
class ParksViewModel(val repository: Repository) : ViewModel() {

    val parksLiveData = repository.getParks()

    init {
        fetchParks()
    }


}


fun ParksViewModel.fetchParks() {
    viewModelScope.launch {
        repository.fetchParks()
        repository.updateVisits()
    }
}