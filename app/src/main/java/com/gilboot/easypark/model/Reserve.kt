package com.gilboot.easypark.model

import android.os.Parcelable
import com.gilboot.easypark.database.ReserveTable
import com.gilboot.easypark.util.generateId
import com.gilboot.easypark.util.timeSpent
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Reserve(
    val _id: String,
    val parkId: String,
    val start: Long,
    val end: Long,
    val arrived: Boolean,
    val departed: Boolean,
    val parkName: String
) : Parcelable {
    fun getTimeSpent() = timeSpent(start, end)
}

fun Reserve.asVisitModel(): Visit {
    return Visit(
        _id = _id,
        parkId = parkId,
        start = start,
        end = end,
        arrived = arrived,
        departed = departed
    )
}


fun Reserve.asDatabaseTable(): ReserveTable {
    return ReserveTable(
        _id = _id,
        parkId = parkId,
        start = start,
        end = end,
        arrived = arrived,
        departed = departed,
        parkName = parkName
    )
}