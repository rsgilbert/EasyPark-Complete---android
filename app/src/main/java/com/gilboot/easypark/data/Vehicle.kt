package com.gilboot.easypark.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Vehicle(
    val id: String = "",
    val driverId: String = "",
    val numberplate: String = "",
    val displayPicture: String = ""
) : Parcelable