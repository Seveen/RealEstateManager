package com.openclassrooms.realestatemanager.feature.search

import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.data.model.RealtyQuery
import com.openclassrooms.realestatemanager.data.repository.RealtyRepository
import kotlinx.coroutines.Dispatchers

class SearchViewModel(private val realtyRepository: RealtyRepository) : ViewModel() {

    private var _currentQuery: MutableLiveData<RealtyQuery> = MutableLiveData(RealtyQuery.default())
    val currentQuery: LiveData<RealtyQuery>
        get() = _currentQuery

    val searchResult = realtyRepository
            .getSearchResult()
            .asLiveData(Dispatchers.Default + viewModelScope.coroutineContext)

    fun changeLowestPrice(value: Double) {
        _currentQuery.value = _currentQuery.value?.copy(lowestPrice = value)
    }

    fun changeHighestPrice(value: Double) {
        _currentQuery.value = _currentQuery.value?.copy(highestPrice = value)
    }

    fun search() {
        _currentQuery.value?.let {
            realtyRepository.currentQuery = it
        }
    }

    fun setCurrentRealty(realty: Realty) {
        realtyRepository.setCurrentRealty(realty)
    }
}