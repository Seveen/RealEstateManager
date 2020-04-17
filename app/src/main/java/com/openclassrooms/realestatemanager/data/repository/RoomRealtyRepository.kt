package com.openclassrooms.realestatemanager.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import com.openclassrooms.realestatemanager.data.database.RealtyDao
import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.data.model.RealtyQuery
import com.openclassrooms.realestatemanager.data.service.GeocodingClient
import kotlinx.coroutines.flow.Flow

class RoomRealtyRepository(
        private val realtyDao: RealtyDao,
        private val geocodingClient: GeocodingClient
) : RealtyRepository {

    private var _currentRealty: MutableLiveData<Realty> = MutableLiveData()

    override val currentRealty
        get() = _currentRealty

    override var currentQuery: RealtyQuery = RealtyQuery.default()

    override fun setCurrentRealty(realty: Realty?) {
        _currentRealty.value = realty
    }

    override suspend fun saveCurrentRealty(isNetworkAvailable: Boolean): Boolean {
        currentRealty.value?.let {
            return if (isNetworkAvailable) saveRealty(it)
            else saveRealtyOffline(it)
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

    override suspend fun saveRealtyOffline(realty: Realty): Boolean {
        Log.d(javaClass.canonicalName, "Saving offline")

        if (realty.photos.isEmpty()) return false

        when (realty.id) {
            0 -> realtyDao.insert(realty)
            else -> realtyDao.update(realty)
        }
        return true
    }

    override suspend fun updateGeolocation(realty: Realty) {
        realtyDao.update(realty.copy(location = geocodingClient.getGeocoding(realty.address)))
    }

    override fun getAllRealty(): Flow<List<Realty>> =
        realtyDao.getAllRealtyDistinctUntilChanged()

    override fun getRealtyById(id: Int): Flow<Realty> =
        realtyDao.getRealtyById(id)

    override fun getRealtyViaQuery(query: RealtyQuery): Flow<List<Realty>> {
        val (stringQuery, bindParameters) = query.toSQLQuery()

        return realtyDao.getRealtyViaQuery(
                SimpleSQLiteQuery(stringQuery, bindParameters))
    }

    override fun getSearchResult(): Flow<List<Realty>> =
        getRealtyViaQuery(currentQuery)

}