package com.gilboot.easypark

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.gilboot.easypark.database.Dao
import com.gilboot.easypark.database.asModel
import com.gilboot.easypark.model.Park
import com.gilboot.easypark.model.Visit
import com.gilboot.easypark.network.DriverNetwork
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

    suspend fun loginPark(
        email: String,
        password: String
    ): Boolean? {
        return try {
            val parkNetwork: ParkNetwork =
                getNetworkService().postParkLogin(email, password)
            dao.insertOnePark(parkNetwork.asDatabaseTable())
            true
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun loginDriver(
        email: String,
        password: String
    ): Boolean? {
        return try {
            val driverNetwork: DriverNetwork =
                getNetworkService().postDriverLogin(email, password)
            dao.insertOneDriver(driverNetwork.asDatabaseTable())
            true
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun signupDriver(
        email: String,
        password: String
    ): Boolean? {
        return try {
            val driverNetwork: DriverNetwork =
                getNetworkService().postDriverSignup(email, password)
            dao.insertOneDriver(driverNetwork.asDatabaseTable())
            true
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun fetchParks() {
        try {
            val parks: List<ParkNetwork> = getNetworkService().fetchParks()
            dao.insertParks(parks.asDatabaseTable())
        } catch (e: Exception) {
            null
        }
    }

    fun getParks(): LiveData<List<Park>> {
        return dao.getParks().map { it.asModel() }
    }


    fun getVisits(): LiveData<List<Visit>> {
        return dao.getOnePark().switchMap {
            dao.getVisits(it._id).map { it.asModel() }
        }

    }
}