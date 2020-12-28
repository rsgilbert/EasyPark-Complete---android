package com.gilboot.easypark.network

import com.gilboot.easypark.database.VisitTable


data class VisitNetwork(
    val _id: String,
    val parkId: String,
    val numberplate: String,
    val start: Long,
    val end: Long,
    val complete: Boolean,
    val arrived: Boolean,
    val departed: Boolean
)

fun VisitNetwork.asDatabaseTable(): VisitTable {
    return VisitTable(
        _id = _id,
        parkId = parkId,
        start = start,
        end = end,
        arrived = arrived,
        departed = departed
    )
}

fun List<VisitNetwork>.asDatabaseTable() = map { it.asDatabaseTable() }