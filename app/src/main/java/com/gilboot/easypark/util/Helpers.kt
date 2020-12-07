package com.gilboot.easypark.util

import android.content.Context
import android.content.SharedPreferences
import com.gilboot.easypark.R
import com.gilboot.easypark.data.User
import com.google.gson.Gson
import java.util.*


fun flatLatLng(latitude: Double, longitude: Double) =
    "${latitude},${longitude}"


fun Context.getSharedPrefs(): SharedPreferences = getSharedPreferences(
    getString(R.string.preferences_filename), Context.MODE_PRIVATE
)


fun jsonToUser(json: String?): User? = json?.let { Gson().fromJson(json, User::class.java) }

fun User.toJson(): String = Gson().toJson(this)

fun Context.saveUserToPrefs(user: User) =
    getSharedPrefs().edit().putString("user", user.toJson()).apply()

fun Context.getUserFromPrefs(): User? = jsonToUser(getSharedPrefs().getString("user", ""))


// remove user from shared preferences by setting user to empty string
fun Context.removeUserFromPrefs() =
    getSharedPrefs().edit().putString("user", "").apply()


// simple function to generate id
fun generateId(): String = Date().time.toString()