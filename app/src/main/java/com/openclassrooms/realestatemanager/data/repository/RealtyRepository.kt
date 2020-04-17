package com.openclassrooms.realestatemanager.data.repository

import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.data.model.RealtyQuery
import kotlinx.coroutines.flow.Flow

interface RealtyRepository {
    val currentRealty: LiveData<Realty?>
    var currentQuery: RealtyQuery

    fun setCurrentRealty(realty: Realty?)
    suspend fun saveCurrentRealty(isNetworkAvailable: Boolean): Boolean
    suspend fun saveRealty(realty: Realty): Boolean
    suspend fun saveRealtyOffline(realty: Realty): Boolean
    suspend fun updateGeolocation(realty: Realty)
    fun getAllRealty(): Flow<List<Realty>>
    fun getRealtyById(id: Int): Flow<Realty>
    fun getRealtyViaQuery(query: RealtyQuery): Flow<List<Realty>>
    fun getSearchResult(): Flow<List<Realty>>
}