package com.gilboot.easypark.util

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.gilboot.easypark.R
import com.gilboot.easypark.Repository
import com.gilboot.easypark.database.Database
import com.gilboot.easypark.model.User
import com.google.gson.Gson
import java.util.*


fun flatLatLng(latitude: Double, longitude: Double) =
    "${latitude},${longitude}"


fun Context.getSharedPrefs(): SharedPreferences = getSharedPreferences(
    getString(R.string.preferences_filename), Context.MODE_PRIVATE
)


fun jsonToUser(json: String?): User? = json?.let {
    Gson().fromJson(json, User::class.java)
}

fun User.toJson(): String = Gson().toJson(this)

fun Context.saveUserToPrefs(user: User) =
    getSharedPrefs().edit().putString("user", user.toJson()).apply()

fun Context.getUserFromPrefs(): User? = jsonToUser(getSharedPrefs().getString("user", null))

fun Fragment.getUserFromPrefs() = requireContext().getUserFromPrefs()

val Fragment.parkIdFromPrefs : String?
    get() = getUserFromPrefs()?._id

// remove user from shared preferences by setting user to empty string
fun Context.removeUserFromPrefs() =
    getSharedPrefs().edit().remove("user").apply()


// simple function to generate id
fun generateId(): String = Date().time.toString()


// check network
fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
}


fun EditText.isValidInput(): Boolean {
    return text.isNotBlank()
}

fun EditText.textValue(): String {
    return text.toString()
}

val Fragment.repository: Repository
    get() {
        val activity = requireNotNull(activity)
        val database = Database.getInstance(activity)
        return Repository(database.dao)
    }