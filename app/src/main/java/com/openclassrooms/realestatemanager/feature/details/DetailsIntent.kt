package com.openclassrooms.realestatemanager.feature.details

import com.openclassrooms.realestatemanager.mvibase.MviIntent

sealed class DetailsIntent: MviIntent {
    object LoadRealtyDetailsIntent : DetailsIntent()
}