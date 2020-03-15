package com.openclassrooms.realestatemanager.feature.allrealty

import com.openclassrooms.realestatemanager.mvibase.MviAction

sealed class AllRealtyAction : MviAction {
    object LoadAllRealtyAction : AllRealtyAction()
}