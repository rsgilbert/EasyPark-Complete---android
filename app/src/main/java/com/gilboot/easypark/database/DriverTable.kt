package com.gilboot.easypark.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import com.gilboot.easypark.model.Driver


@Entity
data class DriverTable(
    @PrimaryKey
    val _id: String,
    val email: String,
    val password: String
)

fun DriverTable.asModel(): Driver {
    return Driver(_id = _id, email = email, password = password)
}