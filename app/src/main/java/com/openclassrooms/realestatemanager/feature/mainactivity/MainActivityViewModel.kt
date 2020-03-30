package com.openclassrooms.realestatemanager.feature.mainactivity

import android.util.Log
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.data.repository.RealtyRepository

class MainActivityViewModel(
        private val realtyRepository: RealtyRepository
) : ViewModel() {

    fun clearCurrentRealty() {
        Log.d("REPOSITORYLOG", "from mainVM")
        realtyRepository.setCurrentRealty(Realty.default())
    }
}