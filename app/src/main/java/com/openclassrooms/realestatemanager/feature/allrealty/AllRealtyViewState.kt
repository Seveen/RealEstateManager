package com.openclassrooms.realestatemanager.feature.allrealty

import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.mvibase.MviViewState

data class AllRealtyViewState(
        val isLoading: Boolean,
        val realty: List<Realty>,
        val error: Throwable?
) : MviViewState {
    companion object {
        fun idle(): AllRealtyViewState = AllRealtyViewState(
                isLoading = false,
                realty = emptyList(),
                error = null
        )
    }
}