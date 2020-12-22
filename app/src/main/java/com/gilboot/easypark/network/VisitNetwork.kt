package com.gilboot.easypark.network

import com.gilboot.easypark.database.VisitTable


data class VisitNetwork(
    val _id: String,
    val parkId: String,
    val numberplate: String,
    val start: Long,
    val end: Long,
    val complete: Boolean
)

fun VisitNetwork.asDatabaseTable(): VisitTable {
    return VisitTable(
        _id = _id,
        parkId = parkId,
        numberplate = numberplate,
        start = start,
        end = end,
        complete = complete
    )
}

fun List<VisitNetwork>.asDatabaseTable() = map { it.asDatabaseTable() }