package com.gilboot.easypark.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilboot.easypark.Repository
import com.gilboot.easypark.model.Visit
import com.gilboot.easypark.util.visitCollection
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.launch
import timber.log.Timber


// ViewModel for DashboardFragment
class DashboardViewModel(val repository: Repository) : ViewModel() {
    val visitsLiveData = repository.getVisits()

    init {
        getVisits()
    }

}

fun DashboardViewModel.getVisits() {
    viewModelScope.launch {
    }
}