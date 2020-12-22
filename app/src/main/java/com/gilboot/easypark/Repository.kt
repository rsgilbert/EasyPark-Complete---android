package com.gilboot.easypark

import com.gilboot.easypark.database.Dao
import com.gilboot.easypark.database.asModel
import com.gilboot.easypark.model.Park
import com.gilboot.easypark.network.ParkNetwork
import com.gilboot.easypark.network.asDatabaseTable
import retrofit2.http.Field
import java.lang.Exception


class Repository(private val dao: Dao) {

    suspend fun signupPark(
        email: String,
        password: String,
        name: String,
        tel: String,
        lat: Double,
        lng: Double,
        picture: String
    ): Boolean? {
        return try {
            val parkNetwork: ParkNetwork =
                getNetworkService().postParkSignup(email, password, name, tel, lat, lng, picture)
            dao.insertOnePark(parkNetwork.asDatabaseTable())
            true
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}