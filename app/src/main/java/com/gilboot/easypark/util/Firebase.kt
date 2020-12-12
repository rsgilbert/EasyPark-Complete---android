package com.gilboot.easypark.util

import androidx.fragment.app.Fragment
import com.gilboot.easypark.data.Driver
import com.gilboot.easypark.data.Park
import com.gilboot.easypark.data.Vehicle
import com.gilboot.easypark.data.Visit
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import timber.log.Timber
import java.io.InputStream
import java.util.*


val db: FirebaseFirestore = Firebase.firestore

val storage: FirebaseStorage = Firebase.storage

val userCollection: CollectionReference = db.collection("users")
val vehicleCollection: CollectionReference = db.collection("vehicles")
val parkCollection: CollectionReference = db.collection("parks")
val visitCollection: CollectionReference = db.collection("visits")
val driverCollection: CollectionReference = db.collection("drivers")

fun savePark(park: Park, onSuccess: () -> Unit) {
    parkCollection.document(park.id)
        .set(park, SetOptions.merge())
        .addOnSuccessListener {
            Timber.i("Saved park: $park")
            onSuccess()
        }
}


fun withAuthPark(email: String, password: String, lambda: (park: Park?) -> Unit) {
    parkCollection
        .whereEqualTo("email", email)
        .whereEqualTo("password", password)
        .get()
        .addOnCompleteListener {
            Timber.i("AuthPark: ${it.result}")
            val parks: List<Park> = it.result.toObjects()
            Timber.i("Auth parks got: $parks")
            if (parks.isNotEmpty()) {
                lambda(parks.first())
            } else lambda(null)
        }
}

fun withAuthDriver(email: String, password: String, lambda: (driver: Driver?) -> Unit) {
    driverCollection
        .whereEqualTo("email", email)
        .whereEqualTo("password", password)
        .get()
        .addOnCompleteListener {
            Timber.i("AuthPark: ${it.result}")
            val drivers: List<Driver> = it.result.toObjects()
            Timber.i("Auth parks got: $drivers")
            if (drivers.isNotEmpty()) {
                lambda(drivers.first())
            } else lambda(null)
        }
}

fun withAuthDriverSignup(email: String, password: String, lambda: (driver: Driver?) -> Unit) {
    val driver = Driver(email = email, password = password)
    driverCollection
        .document(driver.id)
        .set(driver, SetOptions.merge())
        .addOnSuccessListener {
            lambda(driver)
        }
        .addOnFailureListener {
            lambda(null)
        }
}

fun Fragment.withUpdateVisit(visitId: String, lambda: () -> Unit) {

    visitCollection.document(visitId)
        .update("complete", true, "end", Date().time)
        .addOnCompleteListener {
            lambda()
        }
}

fun Fragment.withAddVisit(numberplate: String, lambda: (visit: Visit?) -> Unit) {
    val visit = Visit(
        parkId = requireContext().getUserFromPrefs()!!.id,
        numberplate = numberplate,
        start = Date().time
    )
    visitCollection
        .document(visit.id)
        .set(visit, SetOptions.merge())
        .addOnSuccessListener {
            lambda(visit)
        }
        .addOnFailureListener { lambda(null) }
}

fun withAuthParkSignup(park: Park, lambda: (park: Park?) -> Unit) {
    parkCollection
        .document(park.id)
        .set(park, SetOptions.merge())
        .addOnSuccessListener {
            Timber.i("Saved park: $park")
            lambda(park)
        }
        .addOnFailureListener {
            lambda(null)
        }
}


fun saveDriver(driver: Driver, onSuccess: () -> Unit) {
    driverCollection.document(driver.id)
        .set(driver, SetOptions.merge())
        .addOnSuccessListener {
            Timber.i("Saved driver: $driver")
            onSuccess()
        }
}

//val descendingJourneyListQuery: Query = journeyCollection.orderBy("id", Query.Direction.DESCENDING)

val searchCollection: CollectionReference = db.collection("searches")


//fun saveJourney(journey: Journey) {
//    journeyCollection.document(journey.id)
//        .set(journey, SetOptions.merge())
//        .addOnSuccessListener { Timber.i("Saved journey: $journey") }
//}
//
//fun saveSearch(search: Search) {
//    searchCollection.document(search.searchWord)
//        .set(search, SetOptions.merge())
//        .addOnSuccessListener { Timber.i("Saved search: $search") }
//}
//
//
//suspend fun performNewSearch(searchWord: String) {
//    val places: List<Place> = getNetworkService().searchPlaces(searchWord).places
//    val search = Search(searchWord = searchWord, places = places)
//    saveSearch(search)
//}
//

fun withVehicle(vehicleId: String, lambda: (vehicle: Vehicle?) -> Unit) {
    vehicleCollection.document(vehicleId)
        .get()
        .addOnSuccessListener {
            Timber.i("Document is $it")
            lambda(it.toObject())
        }
}


fun uploadPicture(
    stream: InputStream,
    onUpload: (pictureUrl: String) -> Unit,
    onError: (err: String) -> Unit
) {
    val name = Date().time.toString()
    val uploadRef: StorageReference = storage.reference.child("images").child(name)
    uploadRef
        .putStream(stream)
        .continueWithTask { task ->
            if (!task.isSuccessful) {

                Timber.e("Task not successful: Error occurred")
                onError("Failed to upload")
                task.exception?.let {
                    throw it
                }
            }
            uploadRef.downloadUrl
        }
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onUpload(task.result.toString())
            } else {
                Timber.e("Error occurred")
                onError("Error occurred")
            }
        }
}
