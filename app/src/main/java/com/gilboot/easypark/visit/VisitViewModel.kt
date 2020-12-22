package com.gilboot.easypark.visit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gilboot.easypark.model.Visit
import com.gilboot.easypark.util.visitCollection
import com.google.firebase.firestore.ktx.toObject
import timber.log.Timber

class VisitViewModel(val visitId: String) : ViewModel() {

    val visitLiveData = MutableLiveData<Visit>()
    init {
        getVisit()
    }

}

fun VisitViewModel.getVisit() {
    visitCollection
        .document(visitId)
        .addSnapshotListener { value, _ ->
            visitLiveData.value = value!!.toObject()
            Timber.i("Visit: ${value.toObject<Visit>()}")
        }
}