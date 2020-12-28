package com.gilboot.easypark.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import com.gilboot.easypark.model.Park
import com.gilboot.easypark.model.Visit
import com.gilboot.easypark.util.generateId
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize


@Entity
data class ParkTable(
    @PrimaryKey
    val _id: String,
    val email: String,
    val password: String,
    val name: String,
    val tel: String,
    val lat: Double,
    val lng: Double,
    val picture: String,
    val capacity: Int
)

fun ParkTable.asModel(): Park {
    return Park(
        _id = _id,
        email = email,
        password = password,
        name = name,
        tel = tel,
        lat = lat,
        lng = lng,
        picture = picture,
        capacity = capacity
    )
}

fun List<ParkTable>.asModel() = map { it.asModel() }