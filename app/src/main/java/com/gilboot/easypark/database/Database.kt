package com.gilboot.easypark.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gilboot.easypark.database.ParkTable


// class responsible for creating sqlite database
@Database(
    entities = [DriverTable::class, ParkTable::class, VisitTable::class, ReserveTable::class],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract val dao: Dao

    companion object {
        @Volatile
        private var INSTANCE: com.gilboot.easypark.database.Database? = null

        fun getInstance(context: Context): com.gilboot.easypark.database.Database {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        com.gilboot.easypark.database.Database::class.java, "easy_park_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}