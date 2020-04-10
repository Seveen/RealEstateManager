package com.openclassrooms.realestatemanager.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.openclassrooms.realestatemanager.data.model.EstateAgent
import com.openclassrooms.realestatemanager.data.model.Realty

@Database(entities = [Realty::class, EstateAgent::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RealtyDatabase : RoomDatabase() {
    abstract fun realtyDao(): RealtyDao

    companion object {
        @Volatile
        private var INSTANCE: RealtyDatabase? = null

        fun getDatabase(context: Context): RealtyDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        RealtyDatabase::class.java,
                        "realty_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}