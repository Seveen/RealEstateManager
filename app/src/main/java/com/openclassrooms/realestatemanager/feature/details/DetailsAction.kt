package com.openclassrooms.realestatemanager.feature.details

import com.openclassrooms.realestatemanager.mvibase.MviAction

sealed class DetailsAction : MviAction {
    data class LoadRealtyDetailsAction(val realtyId: String) : DetailsAction()
}