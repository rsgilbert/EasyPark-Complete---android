package com.gilboot.easypark.data

import android.os.Parcelable
import com.gilboot.easypark.util.generateId
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Park(
    val id: String = generateId(),
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val tel: String = "",
    val displayPicture: String = "",
    val pictures: List<String> = emptyList(),
    val lat: Double = 0.0,
    val lng: Double = 0.0
) : Parcelable