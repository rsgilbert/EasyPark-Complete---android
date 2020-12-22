package com.gilboot.easypark.network

import com.gilboot.easypark.database.DriverTable


data class DriverNetwork(
    val _id: String,
    val email: String,
    val password: String
)

fun DriverNetwork.asDatabaseTable(): DriverTable {
    return DriverTable(_id = _id, email = email, password = password)
}

