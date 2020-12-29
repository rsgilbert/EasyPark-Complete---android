package com.gilboot.easypark.network

import com.gilboot.easypark.database.VisitTable


data class VisitNetwork(
    val _id: String,
    val parkId: String?,
    val start: Long?,
    val end: Long?,
    val complete: Boolean?,
    val arrived: Boolean?,
    val departed: Boolean?,
    val parkName: String?,
    val driverId: String?
)

fun VisitNetwork.asDatabaseTable(): VisitTable {
    return VisitTable(
        _id = _id,
        parkId = parkId!!,
        start = start!!,
        end = end!!,
        arrived = arrived!!,
        departed = departed!!,
        parkName = parkName!!,
        driverId = driverId!!
    )
}

// Convert visitNetworks into visitTables skipping incompatible visitNetworks
fun List<VisitNetwork>.asDatabaseTable(): List<VisitTable> {
    val visitTables = mutableListOf<VisitTable>()
    for (v in this) {
        try {
            visitTables.add(v.asDatabaseTable())
        } catch (e: Exception) {
            continue
        }
    }
    return visitTables
}