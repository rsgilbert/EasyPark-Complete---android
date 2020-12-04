package com.gilboot.easypark.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class User(
    val id: String = "",
    val isDriver: Boolean = true,
    val email: String = "",
    val password: String = ""
) : Parcelable