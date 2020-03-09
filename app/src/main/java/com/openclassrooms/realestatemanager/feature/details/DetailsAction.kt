package com.openclassrooms.realestatemanager.feature.details

import com.openclassrooms.realestatemanager.mvibase.MviAction

sealed class DetailsAction : MviAction {
    object LoadRealtyDetailsAction : DetailsAction()
}