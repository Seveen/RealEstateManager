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

    fun changeLowestPrice(value: Double?) {
        _currentQuery.value = _currentQuery.value?.copy(lowestPrice = value)
    }

    fun changeHighestPrice(value: Double?) {
        _currentQuery.value = _currentQuery.value?.copy(highestPrice = value)
    }

    fun changeMinimumSurface(value: Double?) {
       _currentQuery.value = _currentQuery.value?.copy(minimumSurface = value)
    }

    fun changeMaximumSurface(value: Double?) {
        _currentQuery.value = _currentQuery.value?.copy(maximumSurface = value)
    }

    fun changeMinimumNbRooms(value: Int?) {
        _currentQuery.value = _currentQuery.value?.copy(minimumNbRooms = value)
    }

    fun changeMinimumNbBathrooms(value: Int?) {
        _currentQuery.value = _currentQuery.value?.copy(minimumNbBathrooms = value)
    }

    fun changeMinimumNbBedrooms(value: Int?) {
        _currentQuery.value = _currentQuery.value?.copy(minimumNbBedrooms = value)
    }

    fun changeDistrict(value: String?) {
        _currentQuery.value = _currentQuery.value?.copy(district = value)
    }

    fun changeCloseParks(value: Boolean) {
        _currentQuery.value = _currentQuery.value?.copy(isCloseToPark = value)
    }

    fun changeCloseSubway(value: Boolean) {
        _currentQuery.value = _currentQuery.value?.copy(isCloseToSubway = value)
    }

    fun changeCloseShops(value: Boolean) {
        _currentQuery.value = _currentQuery.value?.copy(isCloseToShops = value)
    }

    fun changeHouseType(value: Boolean) {
        _currentQuery.value?.let {
            _currentQuery.value = if (value) {
                 _currentQuery.value?.copy(types = it.types.plus("House"))
            } else {
                _currentQuery.value?.copy(types = it.types.minus("House"))
            }
        }
    }

    fun changeFlatType(value: Boolean) {
        _currentQuery.value?.let {
            _currentQuery.value = if (value) {
                _currentQuery.value?.copy(types = it.types.plus("Flat"))
            } else {
                _currentQuery.value?.copy(types = it.types.minus("Flat"))
            }
        }
    }

    fun changeDuplexType(value: Boolean) {
        _currentQuery.value?.let {
            _currentQuery.value = if (value) {
                _currentQuery.value?.copy(types = it.types.plus("Duplex"))
            } else {
                _currentQuery.value?.copy(types = it.types.minus("Duplex"))
            }
        }
    }

    fun changePenthouseType(value: Boolean) {
        _currentQuery.value?.let {
            _currentQuery.value = if (value) {
                _currentQuery.value?.copy(types = it.types.plus("Penthouse"))
            } else {
                _currentQuery.value?.copy(types = it.types.minus("Penthouse"))
            }
        }
    }

    fun search() {
        _currentQuery.value?.let {
            realtyRepository.currentQuery = it
        }
    }

    fun clear() {
        _currentQuery.value = RealtyQuery.default()
    }

    fun setCurrentRealty(realty: Realty) {
        realtyRepository.setCurrentRealty(realty)
    }
}