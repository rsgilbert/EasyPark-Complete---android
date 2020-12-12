package com.gilboot.easypark.data

import android.os.Parcelable
import com.gilboot.easypark.util.generateId
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Visit(
    val id: String = generateId(),
    val userId: String = "",
    val parkId: String = "",
    val vehicleId: String = "",
    val start: Long = 0L,
    val end: Long = 0L
) : Parcelable