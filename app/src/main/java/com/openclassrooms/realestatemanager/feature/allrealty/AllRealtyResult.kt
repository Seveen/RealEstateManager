package com.openclassrooms.realestatemanager.feature.allrealty

import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.mvibase.MviResult

sealed class AllRealtyResult : MviResult {
    sealed class LoadAllRealtyResult : AllRealtyResult() {
        object Loading : LoadAllRealtyResult()
        data class Success(val realty: List<Realty>) : LoadAllRealtyResult()
        data class Failure(val error: Throwable) : LoadAllRealtyResult()
    }
    sealed class NavigateToDetailsResult : AllRealtyResult() {
        object Success : NavigateToDetailsResult()
        data class Failure(val error: Throwable) : NavigateToDetailsResult()
    }
}