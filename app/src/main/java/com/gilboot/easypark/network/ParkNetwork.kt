package com.gilboot.easypark.network

import androidx.room.PrimaryKey
import com.gilboot.easypark.database.ParkTable


data class ParkNetwork(
    val _id: String,
    val email: String,
    val password: String,
    val name: String,
    val tel: String,
    val lat: Double,
    val lng: Double,
    val picture: String
)

fun ParkNetwork.asDatabaseTable(): ParkTable {
    return ParkTable(
        _id = _id,
        email = email,
        password = password,
        name = name,
        tel = tel,
        lat = lat,
        lng = lng,
        picture = picture
    )
}

fun List<ParkNetwork>.asDatabaseTable() = map { it.asDatabaseTable() }