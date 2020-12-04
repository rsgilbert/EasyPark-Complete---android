package com.gilboot.easypark.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Vehicle(
    val id: String = "",
    val userId: String = "",
    val numberplate: String = "",
    val pictures: List<String> = emptyList()
) : Parcelable