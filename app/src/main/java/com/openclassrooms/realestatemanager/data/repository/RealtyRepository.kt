package com.openclassrooms.realestatemanager.data.repository

import androidx.lifecycle.LiveData
import com.openclassrooms.realestatemanager.data.model.Realty
import kotlinx.coroutines.flow.Flow

interface RealtyRepository {
    val currentRealty: LiveData<Realty?>
    fun setCurrentRealty(realty: Realty?)

    fun saveRealty(realty: Realty): Flow<Result<Boolean>>
    fun getAllRealty(): Flow<Result<List<Realty>>>
    fun clearAllRealty(): Flow<Result<Boolean>>
    fun getRealtyById(id: String): Flow<Result<Realty>>
}