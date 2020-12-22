package com.gilboot.easypark.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface Dao {
    // Queries
    @Query("SELECT * FROM ParkTable")
    fun getParks(): LiveData<List<ParkTable>>

    @Query("SELECT * FROM VisitTable")
    fun getVisits(): LiveData<List<VisitTable>>


    // Inserts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOnePark(parkTable: ParkTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParks(parkTables: List<ParkTable>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOneVisit(visitTable: VisitTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVisits(visitTables: List<VisitTable>)
}