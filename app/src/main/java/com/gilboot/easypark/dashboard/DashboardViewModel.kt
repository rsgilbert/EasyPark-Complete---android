package com.gilboot.easypark.dashboard

import androidx.lifecycle.*
import com.gilboot.easypark.Repository
import com.gilboot.easypark.model.Visit
import com.gilboot.easypark.util.visitCollection
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.launch
import timber.log.Timber


// ViewModel for DashboardFragment
class DashboardViewModel(val repository: Repository) : ViewModel() {
    val parkId = repository.getParkId()
    val visitsLiveData = repository.getVisits()

    val availableSlotsLiveData = parkId.switchMap { repository.getCountAvailableSlots(it) }

    val reservedCountLiveData = parkId.switchMap { repository.getCountReservedVisits(it) }

    val capacityLiveData =
        parkId.switchMap { repository.getParkById(it).map { park -> park.capacity } }

    init {
        updateVisits()
    }

}

fun DashboardViewModel.updateVisits() {
    viewModelScope.launch {
        repository.updateVisits()
    }
}