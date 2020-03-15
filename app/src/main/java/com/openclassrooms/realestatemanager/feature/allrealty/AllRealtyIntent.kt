package com.openclassrooms.realestatemanager.feature.allrealty

import com.openclassrooms.realestatemanager.mvibase.MviIntent

sealed class AllRealtyIntent : MviIntent {
    object LoadAllRealtyIntent : AllRealtyIntent()
}