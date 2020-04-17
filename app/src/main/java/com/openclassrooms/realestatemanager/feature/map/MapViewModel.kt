package com.openclassrooms.realestatemanager.feature.map

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationRequest
import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.data.repository.LocationRepository
import com.openclassrooms.realestatemanager.data.repository.RealtyRepository
import kotlinx.coroutines.Dispatchers

class MapViewModel(
        private val realtyRepository: RealtyRepository,
        private val locationRepository: LocationRepository
) : ViewModel() {

    private val _realtyList = realtyRepository
            .getAllRealty()
            .asLiveData(Dispatchers.Default + viewModelScope.coroutineContext)

    val realtyList: LiveData<List<Realty>>
        get() = _realtyList

    private val _locationFlow = locationRepository
            .getLocation(1000, LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
            .asLiveData(Dispatchers.Default + viewModelScope.coroutineContext)

    val locationFlow: LiveData<Location>
        get() = _locationFlow

    private fun setCurrentRealty(realty: Realty) {
        realtyRepository.setCurrentRealty(realty)
    }

    fun setCurrentRealtyById(id: Int) {
        realtyList.value?.find { it.id == id }?.let {
            setCurrentRealty(it)
        }
    }
}