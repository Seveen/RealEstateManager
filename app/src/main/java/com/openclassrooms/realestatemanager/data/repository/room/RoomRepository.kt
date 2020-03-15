package com.openclassrooms.realestatemanager.data.repository.room

import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.data.repository.RealtyRepository
import io.reactivex.Observable

class RoomRepository : RealtyRepository {
    override fun saveRealty(realty: Realty): Observable<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllRealty(): Observable<List<Realty>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearAllRealty(): Observable<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRealtyById(id: String): Observable<Realty> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}