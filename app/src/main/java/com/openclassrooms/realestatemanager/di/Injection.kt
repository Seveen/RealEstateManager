package com.openclassrooms.realestatemanager.di

import com.openclassrooms.realestatemanager.model.RealtyModelStore

//TODO("Use koin?")
class Injection {
    fun getRealtyModelStore() = RealtyModelStore
}