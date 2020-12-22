package com.gilboot.easypark.model

import android.os.Parcelable
import com.gilboot.easypark.util.generateId
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Vehicle(
    val id: String = generateId(),
    val driverId: String = "",
    val numberplate: String = "",
    val displayPicture: String = ""
) : Parcelable