package com.gilboot.easypark.data

import android.os.Parcelable
import com.gilboot.easypark.util.generateId
import com.gilboot.easypark.util.timeSpent
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Visit(
    val id: String = generateId(),
    val parkId: String = "",
    val numberplate: String = "",
    val start: Long = 0L,
    val end: Long = 0L,
    val complete: Boolean = false
) : Parcelable {
    fun getTimeSpent() = timeSpent(start, end)
}