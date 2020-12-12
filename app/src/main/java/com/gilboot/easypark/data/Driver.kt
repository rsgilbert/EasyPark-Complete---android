package com.gilboot.easypark.data

import android.os.Parcelable
import com.gilboot.easypark.util.generateId
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Driver(
    val id: String = generateId(),
    val email: String = "",
    val password: String = ""
) : Parcelable {
    @IgnoredOnParcel
    val user: User = User(id, UserType.Driver)
}