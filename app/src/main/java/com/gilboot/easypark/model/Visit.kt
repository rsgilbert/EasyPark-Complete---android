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
    val departed: Boolean
) : Parcelable {
    fun getTimeSpent() = timeSpent(start, end)
}


fun Visit.asReserveModel(parkName: String): Reserve {
    return Reserve(
        _id = _id,
        parkId = parkId,
        start = start,
        end = end,
        arrived = arrived,
        departed = departed,
        parkName = parkName
    )
}
