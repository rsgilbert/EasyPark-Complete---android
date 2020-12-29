package com.gilboot.easypark.dashboard

import androidx.lifecycle.*
import com.gilboot.easypark.Event
import com.gilboot.easypark.Repository
import com.gilboot.easypark.model.Visit
import kotlinx.coroutines.launch


// ViewModel for DashboardFragment
class DashboardViewModel(val repository: Repository) : ViewModel() {
    val parkId = repository.getParkId()

    val availableSlotsLiveData = parkId.switchMap { repository.getCountAvailableSlots(it) }

    val reservedCountLiveData = parkId.switchMap { repository.getCountReservedVisits(it) }

    val capacityLiveData =
        parkId.switchMap { repository.getParkById(it).map { park -> park.capacity } }

    // set up sending snack messages
    val snackMessageLiveData = MutableLiveData<Event<String>>()
    fun setSnackMessage(message: String) {
        snackMessageLiveData.value = Event(message)
    }


    init {
        updateVisits()
    }

}

fun DashboardViewModel.updateVisits() {
    viewModelScope.launch {
        repository.updateVisits()
    }
}

fun DashboardViewModel.processArrival(visitId: String) {
    viewModelScope.launch {
        val visit: Visit? = repository.getVisitSuspension(visitId)
        if (visit == null) {
            setSnackMessage("The reservation does not exist")
            return@launch
        }
        if (visit.arrived) {
            setSnackMessage("The driver has already arrived")
            return@launch
        }
        val attendedVisit = repository.attendVisit(visit)
        if (attendedVisit == null) {
            setSnackMessage("Failed to process arrival")
            return@launch
        }
        setSnackMessage("Successfully processed arrival")
    }
}

fun DashboardViewModel.processDeparture(visitId: String) {
    viewModelScope.launch {
        val visit: Visit? = repository.getVisitSuspension(visitId)
        if (visit == null) {
            setSnackMessage("The reservation does not exist")
            return@launch
        }
        if (!visit.arrived) {
            setSnackMessage("The driver has not yet arrived")
            return@launch
        }
        if (visit.departed) {
            setSnackMessage("The driver has already departed")
            return@launch
        }
        val completedVisit = repository.completeVisit(visit)
        if (completedVisit == null) {
            setSnackMessage("Failed to process departure")
            return@launch
        }
        setSnackMessage("Successfully processed departure")
    }
}