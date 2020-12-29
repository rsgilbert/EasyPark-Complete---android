package com.gilboot.easypark.model

import android.os.Parcelable
import com.gilboot.easypark.util.generateId
import com.gilboot.easypark.util.timeSpent
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Visit(
    val _id: String,
    val parkId: String,
    val start: Long,
    val end: Long,
    val arrived: Boolean,
    val departed: Boolean,
    val parkName: String,
    val driverId: String
) : Parcelable {
    fun getTimeSpent() = timeSpent(start, end)
}



