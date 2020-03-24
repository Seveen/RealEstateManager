package com.openclassrooms.realestatemanager.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.openclassrooms.realestatemanager.data.model.EstateAgent
import com.openclassrooms.realestatemanager.data.model.PointsOfInterest
import com.openclassrooms.realestatemanager.data.model.Realty
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

//TODO: implement
class DebugRepository : RealtyRepository {

    val dummyList: List<Realty> = listOf(
            Realty("0", "House", 100.0, 59.0, 5, 5, 5, "district", "address", PointsOfInterest(1, closeToMetro = false, closeToShops = false), false, Date(), Date(), EstateAgent(0, "agent", "agent"), "description1", emptyList()),
            Realty("1", "Penthouse", 140.0, 59.0, 5, 5, 5, "district", "address", PointsOfInterest(2, closeToMetro = false, closeToShops = false), false, Date(), Date(), EstateAgent(0, "agent", "agent"), "description2", emptyList()),
            Realty("2", "type", 140.0, 59.0, 5, 5, 5, "district", "address", PointsOfInterest(3, closeToMetro = false, closeToShops = false), false, Date(), Date(), EstateAgent(0, "agent", "agent"), "description3", emptyList())
    )

    private var _currentRealty: MutableLiveData<Realty> = MutableLiveData()

    override val currentRealty
        get() = _currentRealty

    override fun setCurrentRealty(realty: Realty?) {
        Log.d("REPOSITORYLOG", "realty changed: ${realty?.description} nb: ${realty?.numberOfRooms}" )
        _currentRealty.value = realty
    }

    override fun saveRealty(realty: Realty): Flow<Result<Boolean>> = flow {
        emit(Result.success(true))
    }

    override fun getAllRealty(): Flow<Result<List<Realty>>> = flow {
        emit(Result.success(dummyList))
    }

    override fun clearAllRealty(): Flow<Result<Boolean>> = flow {
        emit(Result.success(true))
    }

    override fun getRealtyById(id: String): Flow<Result<Realty>> = flow {
        val realty = dummyList.find { it.id == id }
        if (realty != null) {
            emit(Result.success(realty))
        } else {
            emit(Result.failure(Throwable("No realty found")))
        }
    }

}