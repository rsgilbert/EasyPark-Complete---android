package com.gilboot.easypark.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Driver(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val tel: String = "",
    val displayPicture: String = ""
) : Parcelable