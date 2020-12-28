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

    // Queries
    @Query("SELECT * FROM ParkTable")
    fun getParks(): LiveData<List<ParkTable>>

    @Query("SELECT * FROM ParkTable WHERE _id = :id LIMIT 1")
    fun getParkById(id: String): LiveData<ParkTable>

    @Query("SELECT * FROM ParkTable LIMIT 1")
    fun getFirstPark(): LiveData<ParkTable>

    @Query("SELECT * FROM VisitTable WHERE parkId = :parkId")
    fun getVisits(parkId: String): LiveData<List<VisitTable>>


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