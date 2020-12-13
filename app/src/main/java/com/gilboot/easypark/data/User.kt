package com.gilboot.easypark.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

enum class UserType { Driver, Park }


@Parcelize
data class User(
    val id: String = "",
    val type: UserType = UserType.Driver
) : Parcelable