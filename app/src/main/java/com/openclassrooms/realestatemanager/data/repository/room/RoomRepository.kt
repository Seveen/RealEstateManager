package com.openclassrooms.realestatemanager.data.repository.room

import com.jakewharton.rxrelay2.PublishRelay
import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.data.repository.RealtyRepository
import io.reactivex.Observable
import java.util.*

//TODO: implement
class RoomRepository : RealtyRepository {

    private var currentRealtyRelay = PublishRelay.create<Realty>()

    val dummyList: List<Realty> = listOf(
            Realty("type", 100.0, 59.0, 5, "description", emptyList(), "address", emptyList(), false, Date(), Date(), "Agent"),
            Realty("type", 140.0, 59.0, 5, "description", emptyList(), "address", emptyList(), false, Date(), Date(), "Agent"),
            Realty("type", 140.0, 59.0, 5, "description", emptyList(), "address", emptyList(), false, Date(), Date(), "Agent")
    )

    override fun saveRealty(realty: Realty): Observable<Boolean> {
        return Observable.just(true)
    }

    override fun getAllRealty(): Observable<List<Realty>> {
        return Observable.just(dummyList)
    }

    override fun clearAllRealty(): Observable<Boolean> {
        return Observable.just(true)
    }

    override fun setCurrentDetailsRealty(realty: Realty): Observable<Boolean> {
        currentRealtyRelay.accept(realty)
        return Observable.just(true)
    }

    override fun getCurrentDetailsRealty(): Observable<Realty> {
        return currentRealtyRelay
    }


}