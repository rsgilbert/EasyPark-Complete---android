package com.gilboot.easypark.util

import com.gilboot.easypark.data.Vehicle
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
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

fun withVehicle(vehicleId: String, lambda: (vehicle: Vehicle) -> Unit) {
    vehicleCollection.document(vehicleId)
        .get()
        .addOnSuccessListener {
            Timber.i("Document is $it")
            lambda(it.toObject()!!)
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
            }
        }
}

fun uploadAudioFile(audioStream: InputStream, onUpload: (audioUrl: String) -> Unit) {
    val uploadRef = storage.reference.child("audios").child(Date().time.toString())
    uploadRef.putStream(audioStream)
        .continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let { throw it }
            }
            uploadRef.downloadUrl
        }
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onUpload(task.result.toString())
                Timber.i("download url is ${task.result}")
            }
        }
}