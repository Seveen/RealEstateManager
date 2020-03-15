package com.openclassrooms.realestatemanager.data.repository

import com.openclassrooms.realestatemanager.data.model.Realty
import io.reactivex.Observable

interface RealtyRepository {
    fun saveRealty(realty: Realty): Observable<Boolean>
    fun getAllRealty(): Observable<List<Realty>>
    fun clearAllRealty(): Observable<Boolean>
    fun getRealtyById(id: String): Observable<Realty>
}