package com.gilboot.easypark.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import com.gilboot.easypark.util.generateId
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Driver(

    val _id: String,
    val email: String,
    val password: String
) : Parcelable {
    @IgnoredOnParcel
    val user: User = User(_id, UserType.Driver)
}