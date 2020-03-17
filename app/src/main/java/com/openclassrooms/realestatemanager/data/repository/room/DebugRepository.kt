package com.openclassrooms.realestatemanager.data.repository.room

import com.openclassrooms.realestatemanager.data.model.EstateAgent
import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.data.repository.RealtyRepository
import io.reactivex.Observable
import java.util.*

//TODO: implement
class DebugRepository : RealtyRepository {

    val dummyList: List<Realty> = listOf(
            Realty("0", "type", 100.0, 59.0, 5, 5, 5, "district", "address", emptyList(), false, Date(), Date(), EstateAgent(0, "agent", "agent"), "description1", emptyList()),
            Realty("1", "type", 140.0, 59.0, 5, 5, 5, "district", "address", emptyList(), false, Date(), Date(), EstateAgent(0, "agent", "agent"), "description2", emptyList()),
            Realty("2", "type", 140.0, 59.0, 5, 5, 5, "district", "address", emptyList(), false, Date(), Date(), EstateAgent(0, "agent", "agent"), "description3", emptyList())
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

    override fun getRealtyById(id: String): Observable<Realty> {
        val realty = dummyList.find { it.id == id }
        realty?.let {
            return Observable.just(it)
        }
        return Observable.error(Throwable("No realty found"))
    }

}