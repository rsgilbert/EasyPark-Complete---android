package com.gilboot.easypark.driver.parks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gilboot.easypark.data.Park
import com.gilboot.easypark.util.parkCollection
import com.google.firebase.firestore.ktx.toObjects
import timber.log.Timber


// holds list of nearby parks to show to the driver
class ParksViewModel : ViewModel() {

    val parksLiveData = MutableLiveData<List<Park>>()

    init {
        setParks()
    }


}

fun ParksViewModel.setParks() {
    parkCollection.addSnapshotListener { snapshot, e ->
        if (e != null) {
            Timber.e("Error listening to park collection")
            return@addSnapshotListener
        }
        parksLiveData.value = snapshot?.toObjects()
    }
}