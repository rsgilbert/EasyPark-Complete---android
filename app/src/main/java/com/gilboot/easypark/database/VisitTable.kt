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
    val numberplate: String,
    val start: Long,
    val end: Long,
    val complete: Boolean
)

fun VisitTable.asModel(): Visit {
    return Visit(
        _id = _id,
        parkId = parkId,
        numberplate = numberplate,
        start = start,
        end = end,
        complete = complete
    )
}

fun List<VisitTable>.asModel() = map { it.asModel() }