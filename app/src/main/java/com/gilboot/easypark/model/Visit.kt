package com.gilboot.easypark.model

import android.os.Parcelable
import com.gilboot.easypark.util.generateId
import com.gilboot.easypark.util.timeSpent
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Visit(
    val _id: String,
    val parkId: String,
    val numberplate: String,
    val start: Long,
    val end: Long,
    val complete: Boolean
) : Parcelable {
    fun getTimeSpent() = timeSpent(start, end)
}