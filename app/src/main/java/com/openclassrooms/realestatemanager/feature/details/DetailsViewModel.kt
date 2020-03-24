package com.openclassrooms.realestatemanager.feature.details

import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.data.repository.RealtyRepository

class DetailsViewModel(
        private val realtyRepository: RealtyRepository
) : ViewModel() {

    val currentRealty = realtyRepository.currentRealty
}