package com.gilboot.easypark.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.gilboot.easypark.Repository
import kotlinx.coroutines.launch


// ViewModel for DashboardFragment
class DashboardViewModel(val repository: Repository) : ViewModel() {
    val parkId = repository.getParkId()

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