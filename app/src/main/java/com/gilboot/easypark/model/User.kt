package com.gilboot.easypark.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

enum class UserType { Driver, Park, None }


// Saved as a shared preference
// Determines what kind of user is logged in on the phone
@Parcelize
data class User(
    val _id: String,
    val type: UserType = UserType.Driver
) : Parcelable