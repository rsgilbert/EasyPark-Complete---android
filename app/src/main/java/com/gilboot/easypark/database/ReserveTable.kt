package com.gilboot.easypark.database


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gilboot.easypark.model.Reserve
import com.gilboot.easypark.model.Visit


@Entity
data class ReserveTable(
    @PrimaryKey
    val _id: String,
    val parkId: String,
    val start: Long,
    val end: Long,
    val arrived: Boolean,
    val departed: Boolean,
    val parkName: String
)

fun ReserveTable.asVisitModel(): Visit {
    return Visit(
        _id = _id,
        parkId = parkId,
        start = start,
        end = end,
        arrived = arrived,
        departed = departed
    )
}


fun ReserveTable.asModel(): Reserve {
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

fun List<ReserveTable>.asModel() = map { it.asModel() }

fun List<ReserveTable>.asVisitModel() = map { it.asVisitModel() }
