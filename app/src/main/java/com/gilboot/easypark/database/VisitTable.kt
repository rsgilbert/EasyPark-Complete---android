package com.gilboot.easypark.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import com.gilboot.easypark.model.Visit
import com.gilboot.easypark.util.generateId
import com.gilboot.easypark.util.timeSpent
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize


@Entity
data class VisitTable(
    @PrimaryKey
    val _id: String,
    val parkId: String,
    val start: Long,
    val end: Long,
    val arrived: Boolean,
    val departed: Boolean
)

fun VisitTable.asModel(): Visit {
    return Visit(
        _id = _id,
        parkId = parkId,
        start = start,
        end = end,
        arrived = arrived,
        departed = departed
    )
}

fun List<VisitTable>.asModel() = map { it.asModel() }