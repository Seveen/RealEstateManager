package com.openclassrooms.realestatemanager.feature.editrealty

import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.mvibase.MviIntent

sealed class EditRealtyIntent : MviIntent {
    data class TypeIntent(val type: String) : EditRealtyIntent()
    data class DistrictIntent(val district: String) : EditRealtyIntent()
    data class DescriptionIntent(val description: String) : EditRealtyIntent()
    data class SurfaceIntent(val surface: String) : EditRealtyIntent()
    data class NumberOfRoomsIntent(val nbRooms: String) : EditRealtyIntent()
    data class NumberOfBathroomsIntent(val nbBathrooms: String) : EditRealtyIntent()
    data class NumberOfBedroomsIntent(val nbBedrooms: String) : EditRealtyIntent()
    data class AddressIntent(val address: String) : EditRealtyIntent()
    data class LoadRealtyIntent(val realtyId: String) : EditRealtyIntent()
    data class SaveIntent(val realty: Realty) : EditRealtyIntent()
}