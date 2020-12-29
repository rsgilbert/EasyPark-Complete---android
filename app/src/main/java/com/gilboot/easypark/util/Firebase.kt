package com.gilboot.easypark.util


import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import timber.log.Timber
import java.io.InputStream
import java.util.*



val storage: FirebaseStorage = Firebase.storage


/**
 * Upload a picture to firebase
 */
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
