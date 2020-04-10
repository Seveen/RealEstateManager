package com.openclassrooms.realestatemanager.feature.search

import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.data.model.RealtyQuery
import com.openclassrooms.realestatemanager.data.repository.RealtyRepository
import kotlinx.coroutines.Dispatchers

class SearchViewModel(private val realtyRepository: RealtyRepository) : ViewModel() {

    private val _currentQuery = MutableLiveData(RealtyQuery.default())
    val currentQuery: LiveData<RealtyQuery>
        get() = _currentQuery

    public fun changeLowestPrice(value: Double) {
        _currentQuery.value = _currentQuery.value?.copy(lowestPrice = value)
    }

    public fun changeHighestPrice(value: Double) {
        _currentQuery.value = _currentQuery.value?.copy(highestPrice = value)
    }

    public fun search(): LiveData<Realty> {
        return realtyRepository.getRealtyViaQuery(_currentQuery.value!!)
                    .asLiveData(Dispatchers.Default + viewModelScope.coroutineContext)
    }
}