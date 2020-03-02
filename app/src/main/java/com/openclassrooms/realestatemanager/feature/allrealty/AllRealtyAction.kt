package com.openclassrooms.realestatemanager.feature.allrealty

import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.mvibase.MviAction

sealed class AllRealtyAction : MviAction {
    object LoadAllRealtyAction : AllRealtyAction()
    data class NavigateToDetailsAction(val realty: Realty) : AllRealtyAction()
}