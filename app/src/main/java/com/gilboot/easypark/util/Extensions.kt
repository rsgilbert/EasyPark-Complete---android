package com.gilboot.easypark.util

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.gilboot.easypark.model.Visit
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import timber.log.Timber

val Fragment.supportActionBar: ActionBar?
    get() =
        (activity as AppCompatActivity).supportActionBar

fun Fragment.setSupportActionBar(toolbar: Toolbar) =
    (activity as AppCompatActivity).setSupportActionBar(toolbar)


//val Context.googleMapsKey: String
//    get() = getString(R.string.google_maps_key)

val LocalDateTime.millis: Long
    get() = atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

fun Long.toLocalDateTime(): LocalDateTime =
    LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())


val Fragment.writeToStoragePermission: Int
    get() = ContextCompat.checkSelfPermission(
        requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
val Fragment.recordAudioPermission: Int
    get() = ContextCompat.checkSelfPermission(
        requireContext(), Manifest.permission.RECORD_AUDIO
    )
val Fragment.hasRecordingPermissions: Boolean
    get() = recordAudioPermission == PackageManager.PERMISSION_GRANTED

//fun Fragment.requestRecordingPermissions() {
//    ActivityCompat.requestPermissions(
//        requireActivity(),
//        arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE),
//        REQUEST_AUDIO_PERMISSION_CODE
//    )
//}

fun Activity.longSnackbar(text: String) {
    Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG)
        .show()
}

fun Activity.indefiniteSnackbar(text: String) {
    Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_INDEFINITE)
        .show()
}

fun Fragment.longSnackbar(text: String) {
    requireActivity().longSnackbar(text)
}

fun Fragment.indefiniteSnackbar(text: String) {
    requireActivity().indefiniteSnackbar(text)
}

fun Visit.toJson(): String = Gson().toJson(this, Visit::class.java)

fun String.toVisit(): Visit = Gson().fromJson(this, Visit::class.java)


enum class QrSize { SMALL, LARGE }

fun generateQrBitmap(visit: Visit, qrSize: QrSize): Bitmap? {
    val size = when (qrSize) {
        QrSize.SMALL -> 60
        QrSize.LARGE -> 300
    }
    return try {
        val barcodeEncoder = BarcodeEncoder()
        barcodeEncoder.encodeBitmap(visit._id, BarcodeFormat.QR_CODE, size, size)
    } catch (e: Exception) {
        Timber.e("Failed to generate qr code: $e")
        e.printStackTrace()
        null
    }
}

fun ImageView.setQrCode(visit: Visit, qrSize: QrSize) {
    val size = when (qrSize) {
        QrSize.SMALL -> 60
        QrSize.LARGE -> 300
    }
    try {
        val barcodeEncoder = BarcodeEncoder()
        val bitmap =
            barcodeEncoder.encodeBitmap(visit._id, BarcodeFormat.QR_CODE, size, size)
        setImageBitmap(bitmap)
    } catch (e: Exception) {
        Timber.e("Failed to generate qr code: $e")
        e.printStackTrace()
    }
}
