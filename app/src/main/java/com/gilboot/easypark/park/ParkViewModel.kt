package com.gilboot.easypark.park

import android.R
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilboot.easypark.Event
import com.gilboot.easypark.Repository
import com.gilboot.easypark.model.Visit
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.coroutines.launch


class ParkViewModel(val parkId: String, val repository: Repository) : ViewModel() {
    val parkLiveData = repository.getParkById(parkId)

    val availableSlotsLiveData = repository.getCountAvailableSlots(parkId)

    val reservedCountLiveData = repository.getCountReservedVisits(parkId)

    val navigateToReserveLiveData = MutableLiveData<Visit>()

    val isReservedLiveData = repository.isReserved(parkId)

    // set up sending snack messages
    val snackMessageLiveData = MutableLiveData<Event<String>>()
    fun setSnackMessage(message: String) {
        snackMessageLiveData.value = Event(message)
    }
}


/**
 * Functionality for reserving a slot
 * Only reserve a slot when there is still available free slots
 * Save qr code
 */
fun ParkViewModel.reserveSlot() {
    if (availableSlotsLiveData.value!! < 1) {
        setSnackMessage("No available slots")
        return
    }
    // only reserve slot if you haven't already reserved it to the driver
    if (isReservedLiveData.value == true) {
        setSnackMessage("You have already reserved a slot at this park")
        return
    }
    viewModelScope.launch {
        val visit: Visit? = repository.reserveVisit(parkId)
        if (visit == null) {
            setSnackMessage("Network error. Failed to reserve slot")
            return@launch
        }
        navigateToReserveStart(visit)
    }
}

fun ParkViewModel.navigateToReserveStart(reserve: Visit) {
    navigateToReserveLiveData.value = reserve
}

fun ParkViewModel.navigateToReserveComplete() {
    navigateToReserveLiveData.value = null
}

