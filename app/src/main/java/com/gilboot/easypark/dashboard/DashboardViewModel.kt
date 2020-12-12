package com.gilboot.easypark.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gilboot.easypark.data.Visit
import com.gilboot.easypark.util.visitCollection
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObjects
import timber.log.Timber


// ViewModel for DashboardFragment
class DashboardViewModel(val parkId: String) : ViewModel() {
    val visitsLiveData = MutableLiveData<List<Visit>>()

    init {
        getVisits()
    }

}

fun DashboardViewModel.getVisits() {
    visitCollection
        .whereEqualTo("parkId", parkId)
        .whereEqualTo("complete", false)
        .addSnapshotListener { value, _ ->
            val visits: List<Visit> = value?.toObjects() ?: emptyList()
            visitsLiveData.value =  visits.sortedByDescending { v -> v.start }
            Timber.i("Visits: $visits")
        }
}