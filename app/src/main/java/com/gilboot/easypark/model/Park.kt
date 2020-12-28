package com.gilboot.easypark.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.gilboot.easypark.util.generateId
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Park(
    val _id: String,
    val email: String,
    val password: String,
    val name: String,
    val tel: String,
    val lat: Double,
    val lng: Double,
    val picture: String,
    val capacity: Int
) : Parcelable {
    @IgnoredOnParcel
    val user: User = User(_id, UserType.Park)
}

