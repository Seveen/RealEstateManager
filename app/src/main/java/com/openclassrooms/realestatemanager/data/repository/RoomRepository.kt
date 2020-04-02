package com.openclassrooms.realestatemanager.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.data.room.RealtyDao
import com.openclassrooms.realestatemanager.data.service.GeocodingClient
import kotlinx.coroutines.flow.Flow

class RoomRepository(private val realtyDao: RealtyDao, private val geocodingClient: GeocodingClient) : RealtyRepository {

    private var _currentRealty: MutableLiveData<Realty> = MutableLiveData()

    override val currentRealty
        get() = _currentRealty

    override fun setCurrentRealty(realty: Realty?) {
        _currentRealty.value = realty
    }

    override suspend fun saveCurrentRealty(): Boolean {
        currentRealty.value?.let {
            return saveRealty(it)
        }
        return false
    }

    override suspend fun saveRealty(realty: Realty): Boolean {
        Log.d(javaClass.canonicalName, "Saving")

        if (realty.photos.isEmpty()) return false

        val localizedRealty = realty.copy(location = geocodingClient.getGeocoding(realty.address))
        when (localizedRealty.id) {
            0 -> realtyDao.insert(localizedRealty)
            else -> realtyDao.update(localizedRealty)
        }
        return true
    }

    override fun getAllRealty(): Flow<List<Realty>> =
        realtyDao.getAllRealtyDistinctUntilChanged()


    override fun getRealtyById(id: Int): Flow<Realty> =
        realtyDao.getRealtyById(id)

}