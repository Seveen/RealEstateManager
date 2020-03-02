package com.openclassrooms.realestatemanager.app

import android.app.Application
import androidx.room.Room
import com.openclassrooms.realestatemanager.data.repository.room.RealtyDatabase

class RealEstateManagerApplication : Application() {

    companion object {
        lateinit var database: RealtyDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this, RealtyDatabase::class.java, "realty_database")
                .build()
    }
}