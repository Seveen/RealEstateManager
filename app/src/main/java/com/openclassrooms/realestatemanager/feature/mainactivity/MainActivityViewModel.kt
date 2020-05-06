package com.openclassrooms.realestatemanager.feature.mainactivity

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
        realtyRepository.setCurrentRealty(Realty.default())
    }

    fun setRealtyByDefault() {
        viewModelScope.launch {
            realtyRepository.getAllRealty().collect { list ->
                list.firstOrNull()?.let {
                    realtyRepository.setCurrentRealty(it)
                }
            }
        }
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