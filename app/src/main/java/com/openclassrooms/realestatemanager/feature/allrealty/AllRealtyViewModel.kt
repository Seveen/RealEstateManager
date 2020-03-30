package com.openclassrooms.realestatemanager.feature.allrealty

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.data.repository.RealtyRepository
import kotlinx.coroutines.Dispatchers

class AllRealtyViewModel(
        private val realtyRepository: RealtyRepository
) : ViewModel() {

    private val _realtyList = realtyRepository
            .getAllRealty()
            .asLiveData(Dispatchers.Default + viewModelScope.coroutineContext)

    val realtyList: LiveData<List<Realty>>
        get() = _realtyList

    fun setCurrentRealty(realty: Realty) {
        Log.d("REPOSITORYLOG", "from allrealtyvm")
        realtyRepository.setCurrentRealty(realty)
    }
}