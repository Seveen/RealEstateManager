package com.openclassrooms.realestatemanager.feature.details

import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.mvibase.MviViewState

data class DetailsViewState(
        val isLoading: Boolean,
        val realty: Realty?,
        val error: Throwable?
): MviViewState {
    companion object {
        fun idle() = DetailsViewState(
                isLoading = false,
                realty = null,
                error = null
        )
    }
}