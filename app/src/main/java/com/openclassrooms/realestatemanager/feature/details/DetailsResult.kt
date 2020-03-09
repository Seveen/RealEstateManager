package com.openclassrooms.realestatemanager.feature.details

import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.mvibase.MviResult

sealed class DetailsResult : MviResult {
    sealed class LoadRealtyDetailsResult : DetailsResult() {
        object Loading : LoadRealtyDetailsResult()
        data class Success(val realty: Realty) : LoadRealtyDetailsResult()
        data class Failure(val error: Throwable) : LoadRealtyDetailsResult()
    }
}