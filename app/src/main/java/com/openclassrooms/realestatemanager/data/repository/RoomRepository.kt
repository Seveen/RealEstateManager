package com.openclassrooms.realestatemanager.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.data.room.RealtyDao
import kotlinx.coroutines.flow.Flow

class RoomRepository(private val realtyDao: RealtyDao) : RealtyRepository {

    private var _currentRealty: MutableLiveData<Realty> = MutableLiveData()

    override val currentRealty
        get() = _currentRealty

    override fun setCurrentRealty(realty: Realty?) {
        Log.d("REPOSITORYLOG", "realty changed: ${realty?.description} nb: ${realty?.numberOfRooms}" )
        _currentRealty.value = realty
    }

    override suspend fun saveCurrentRealty(): Boolean {
        currentRealty.value?.let {
            return saveRealty(it)
        }
        return false
    }

    override suspend fun saveRealty(realty: Realty): Boolean {
        Log.d(javaClass.canonicalName, "Saved")
        when (realty.id) {
            0 -> realtyDao.insert(realty)
            else -> realtyDao.update(realty)
        }
        return true
    }

    override fun getAllRealty(): Flow<List<Realty>> =
        realtyDao.getAllRealtyDistinctUntilChanged()


    override fun getRealtyById(id: Int): Flow<Realty> =
        realtyDao.getRealtyById(id)

}