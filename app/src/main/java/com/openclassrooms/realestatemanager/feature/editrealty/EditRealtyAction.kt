package com.openclassrooms.realestatemanager.feature.editrealty

import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.mvibase.MviAction

sealed class EditRealtyAction : MviAction {
    data class TypeAction(val type: String) : EditRealtyAction()
    data class DistrictAction(val district: String) : EditRealtyAction()
    data class DescriptionAction(val description: String) : EditRealtyAction()
    data class SurfaceAction(val surface: String) : EditRealtyAction()
    data class NumberOfRoomsAction(val nbRooms: String) : EditRealtyAction()
    data class NumberOfBathroomsAction(val nbBathrooms: String) : EditRealtyAction()
    data class NumberOfBedroomsAction(val nbBedrooms: String) : EditRealtyAction()
    data class AddressAction(val address: String) : EditRealtyAction()
    data class LoadRealtyAction(val realtyId: String) : EditRealtyAction()
    data class SaveAction(val realty: Realty) : EditRealtyAction()
}