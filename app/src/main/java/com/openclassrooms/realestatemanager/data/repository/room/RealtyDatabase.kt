package com.openclassrooms.realestatemanager.data.repository.room

import androidx.room.RoomDatabase

abstract class RealtyDatabase : RoomDatabase() {
    abstract fun realtyDao()
}