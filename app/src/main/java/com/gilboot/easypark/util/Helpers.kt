package com.gilboot.easypark.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.Uri
import android.widget.EditText
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.gilboot.easypark.R
import com.gilboot.easypark.Repository
import com.gilboot.easypark.database.Database
import com.gilboot.easypark.model.User
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

val Fragment.parkIdFromPrefs: String?
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

val Activity.repository: Repository
    get() {
        val database = Database.getInstance(this)
        return Repository(database.dao)
    }
val Fragment.repository: Repository
    get() {
        val activity = requireNotNull(activity)
        val database = Database.getInstance(activity)
        return Repository(database.dao)
    }

fun Fragment.emptyDatabase() {
    viewLifecycleOwner.lifecycleScope.launchWhenCreated {
        withContext(Dispatchers.IO) {
            Database.getInstance(requireActivity()).clearAllTables()
        }
    }
}

fun Context.dialPhoneNumber(phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    }
}