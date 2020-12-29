package com.gilboot.easypark.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface Dao {
    // Count
    @Query("SELECT COUNT(*) FROM VisitTable WHERE parkId = :parkId")
    fun countVisits(parkId: String): LiveData<Int>

    @Query("SELECT COUNT(*) FROM DriverTable")
    suspend fun countDrivers(): Int

    @Query("SELECT COUNT(*) FROM ParkTable")
    suspend fun countParks(): Int

    @Query("SELECT COUNT(*) FROM VisitTable WHERE parkId = :parkId AND driverId = :driverId AND departed = 0")
    fun countIsReserved(parkId: String, driverId: String): LiveData<Int>

    // Queries
    @Query("SELECT * FROM ParkTable")
    fun getParks(): LiveData<List<ParkTable>>

    @Query("SELECT * FROM ParkTable WHERE _id = :id LIMIT 1")
    fun getParkById(id: String): LiveData<ParkTable>

    @Query("SELECT * FROM VisitTable WHERE _id = :visitId LIMIT 1")
    suspend fun getVisitById(visitId: String): VisitTable

    @Query("SELECT * FROM ParkTable WHERE _id = :id LIMIT 1")
    suspend fun getParkByIdSuspension(id: String): ParkTable

    @Query("SELECT * FROM ParkTable LIMIT 1")
    fun getFirstPark(): LiveData<ParkTable>

    @Query("SELECT * FROM DriverTable LIMIT 1")
    fun getFirstDriver(): LiveData<DriverTable>

    @Query("SELECT * FROM DriverTable LIMIT 1")
    suspend fun getFirstDriverSuspension(): DriverTable

    @Query("SELECT * FROM VisitTable WHERE parkId = :parkId")
    fun getVisits(parkId: String): LiveData<List<VisitTable>>

    // we will consider reservations to be all those visits that have not been completed
    @Query("SELECT * FROM VisitTable WHERE departed = 0 AND driverId = :driverId ORDER BY start DESC")
    fun getReservations(driverId: String): LiveData<List<VisitTable>>


    // Inserts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOnePark(parkTable: ParkTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParks(parkTables: List<ParkTable>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOneVisit(visitTable: VisitTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVisits(visitTables: List<VisitTable>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOneDriver(driverTable: DriverTable)

}


//    @Query("SELECT COUNT(1) WHERE EXISTS(SELECT * FROM DriverTable)")
//    fun countOneDriver() : LiveData<Int>