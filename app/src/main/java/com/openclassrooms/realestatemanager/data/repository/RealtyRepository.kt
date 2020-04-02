package com.openclassrooms.realestatemanager.data.repository

import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.data.model.Realty
import kotlinx.coroutines.flow.Flow

interface RealtyRepository {
    val currentRealty: LiveData<Realty?>
    fun setCurrentRealty(realty: Realty?)
    suspend fun saveCurrentRealty(): Boolean
    suspend fun saveCurrentRealtyOffline(): Boolean
    suspend fun saveRealty(realty: Realty): Boolean
    suspend fun saveRealtyOffline(realty: Realty): Boolean
    suspend fun updateGeolocation(realty: Realty)
    fun getAllRealty(): Flow<List<Realty>>
    fun getRealtyById(id: Int): Flow<Realty>
}