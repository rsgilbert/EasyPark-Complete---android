package com.gilboot.easypark

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.gilboot.easypark.database.Dao
import com.gilboot.easypark.database.DriverTable
import com.gilboot.easypark.database.asModel
import com.gilboot.easypark.model.Driver
import com.gilboot.easypark.model.Park
import com.gilboot.easypark.model.UserType
import com.gilboot.easypark.model.Visit
import com.gilboot.easypark.network.DriverNetwork
import com.gilboot.easypark.network.ParkNetwork
import com.gilboot.easypark.network.VisitNetwork
import com.gilboot.easypark.network.asDatabaseTable
import timber.log.Timber
import java.util.*


class Repository(val dao: Dao) {

    fun getDriver(): LiveData<Driver> =
        dao.getFirstDriver().map { it.asModel() }

    suspend fun getDriverSusp(): DriverTable {
        return dao.getFirstDriverSuspension()
    }

    suspend fun getDriverId(): String {
        return getDriverSusp()._id
    }

    fun getParkId(): LiveData<String> =
        dao.getFirstPark().map {
            Timber.i("getting park id as $it")
            it._id
        }

    fun getVisitCount(): LiveData<Int> {
        return getParkId().switchMap { id ->
            Timber.i("ID is $id")
            dao.countVisits(id)
        }
    }

    fun getParkById(parkId: String): LiveData<Park> = dao.getParkById(parkId).map { it.asModel() }

    fun getReserveList(): LiveData<List<Visit>> =
        getDriver().switchMap { driver -> dao.getReservations(driver._id).map { it.asModel() } }

    // return the user type by checking which
    // table has a count of 1 starting with drivers
    suspend fun getUserType(): UserType {
        Timber.i("Drivers: ${dao.countDrivers()}")
        Timber.i("Parks: ${dao.countParks()}")
        return when {
            dao.countDrivers() == 1 -> UserType.Driver
            dao.countParks() == 1 -> UserType.Park
            else -> {
                Timber.i("Drivers: ${dao.countDrivers()}")
                Timber.i("Parks: ${dao.countParks()}")
                UserType.None
            }
        }
    }

    suspend fun signupPark(
        email: String,
        password: String,
        name: String,
        tel: String,
        lat: Double,
        lng: Double,
        picture: String,
        capacity: Int
    ): Boolean? {
        return try {
            val parkNetwork: ParkNetwork =
                getNetworkService().postParkSignup(
                    email,
                    password,
                    name,
                    tel,
                    lat,
                    lng,
                    picture,
                    capacity = capacity
                )
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
            Timber.i(parks.toString())
            dao.insertParks(parks.asDatabaseTable())
        } catch (e: Exception) {
            Timber.e("Failed to fetch parks: $e")
            e.printStackTrace()
            null
        }
    }

    fun getParks(): LiveData<List<Park>> {
        return dao.getParks().map { it.asModel() }
    }


    fun getVisits(): LiveData<List<Visit>> {
        return getParkId().switchMap { id ->
            dao.getVisits(id).map { it.asModel() }
        }
    }


    fun getVisits(parkId: String): LiveData<List<Visit>> =
        dao.getVisits(parkId).map { it.asModel() }


    // fetch visits from backend api and insert them into the visit table
    suspend fun updateVisits() {
        try {
            val visits: List<VisitNetwork> = getNetworkService().fetchVisits()
            dao.insertVisits(visits.asDatabaseTable())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getReservedVisits(parkId: String): LiveData<List<Visit>> =
        getVisits(parkId).map {
            it.filter { v -> !v.arrived }
        }


    // Count reserved slots
    fun getCountReservedVisits(parkId: String): LiveData<Int> =
        getReservedVisits(parkId).map { it.size }


    // The number of available slots = Capacity - (numberOfVisits - numberOfDepartedVisits)
    fun getCountAvailableSlots(parkId: String): LiveData<Int> {
        return getParkById(parkId).switchMap { park ->
            getVisits(park._id).map { visits ->
                val departedVisits = visits.filter { v -> v.departed }
                park.capacity - (visits.size - departedVisits.size)
            }
        }
    }


    // ** IMPORTANT: Called by the Driver **
    // When a driver reserves a slot at the park
    // We set create a visit keeping arrived and departed to false
    suspend fun reserveVisit(parkId: String): Visit? {
        val park = dao.getParkByIdSuspension(parkId)
        return try {
            val visitNetwork: VisitNetwork = getNetworkService().postVisit(
                parkId = parkId,
                start = Date().time,
                end = 0,
                arrived = false,
                departed = false,
                parkName = park.name,
                driverId = getDriverId()
            )
            dao.insertOneVisit(visitNetwork.asDatabaseTable())
            visitNetwork.asDatabaseTable().asModel()
        } catch (e: Exception) {
            Timber.e("Failed to reserve visit: $e")
            e.printStackTrace()
            null
        }
    }

    fun isReserved(parkId: String): LiveData<Boolean> {
        return getDriver().switchMap { driver ->
            dao.countIsReserved(parkId = parkId, driverId = driver._id).map {
                it > 0
            }
        }
    }

    // When a driver enters the park
// We set arrived to true and keep departed false
    suspend fun attendVisit(visit: Visit): Visit? {
        return try {
            val visitNetwork: VisitNetwork = getNetworkService().putVisit(
                _id = visit._id,
                parkId = visit.parkId,
                start = visit.start,
                end = visit.end,
                arrived = true,
                departed = false,
                driverId = visit.driverId
            )
            dao.insertOneVisit(visitNetwork.asDatabaseTable())
            visitNetwork.asDatabaseTable().asModel()
        } catch (e: Exception) {
            Timber.e("Failed to attend visit: $e")
            e.printStackTrace()
            null
        }
    }


    // When the driver leaves the park
// when a visit is completed we set departed to true
    suspend fun completeVisit(visit: Visit): Visit? {
        return try {
            val visitNetwork: VisitNetwork = getNetworkService().putVisit(
                _id = visit._id,
                parkId = visit.parkId,
                start = visit.start,
                end = Date().time,
                arrived = true,
                departed = true,
                driverId = visit.driverId
            )
            dao.insertOneVisit(visitNetwork.asDatabaseTable())
            visitNetwork.asDatabaseTable().asModel()
        } catch (e: Exception) {
            Timber.e("Failed to attend visit: $e")
            e.printStackTrace()
            null
        }
    }
}
