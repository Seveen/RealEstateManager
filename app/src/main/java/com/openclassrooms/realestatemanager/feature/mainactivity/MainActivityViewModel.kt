package com.openclassrooms.realestatemanager.feature.mainactivity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.data.repository.RealtyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(
        private val realtyRepository: RealtyRepository
) : ViewModel() {

    fun clearCurrentRealty() {
        Log.d("REPOSITORYLOG", "from mainVM")
        realtyRepository.setCurrentRealty(Realty.default())
    }

    fun updateGeolocations() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            realtyRepository.getAllRealty().collect {
                it.filter { realty -> realty.location == null }
                        .map { realty -> realtyRepository.updateGeolocation(realty) }
            }
        }
    }
}