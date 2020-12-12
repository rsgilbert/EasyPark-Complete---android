package com.gilboot.easypark.data

import android.os.Parcelable
import com.gilboot.easypark.util.generateId
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Park(
    val id: String = generateId(),
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val tel: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0
) : Parcelable {
    @IgnoredOnParcel
    val user: User = User(id, UserType.Park)
}

