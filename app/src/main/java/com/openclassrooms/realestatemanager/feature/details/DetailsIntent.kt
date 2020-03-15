package com.openclassrooms.realestatemanager.feature.details

import com.openclassrooms.realestatemanager.mvibase.MviIntent

sealed class DetailsIntent: MviIntent {
    data class LoadRealtyDetailsIntent(val realtyId: String) : DetailsIntent()
}