package com.gilboot.easypark.network

import androidx.room.PrimaryKey
import com.gilboot.easypark.database.ParkTable
import com.gilboot.easypark.database.VisitTable


/**
 * Allow for some records to have nullable values during fetch
 * But throw away all records with nullable values during insert
 */
data class ParkNetwork(
    val _id: String,
    val email: String,
    val password: String,
    val name: String?,
    val tel: String?,
    val lat: Double?,
    val lng: Double?,
    val picture: String?,
    val capacity: Int?
)

fun ParkNetwork.asDatabaseTable(): ParkTable {
    return ParkTable(
        _id = _id,
        email = email,
        password = password,
        name = name!!,
        tel = tel!!,
        lat = lat!!,
        lng = lng!!,
        picture = picture!!,
        capacity = capacity!!
    )
}


// Convert visitNetworks into visitTables skipping incompatible visitNetworks
fun List<ParkNetwork>.asDatabaseTable(): List<ParkTable> {
    val parkTables = mutableListOf<ParkTable>()
    for (p in this) {
        try {
            parkTables.add(p.asDatabaseTable())
        } catch (e: Exception) {
            continue
        }
    }
    return parkTables
}