package com.openclassrooms.realestatemanager.feature.editrealty

import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.mvibase.MviResult

sealed class EditRealtyResult : MviResult {
    sealed class TypeResult : EditRealtyResult() {
        object Processing : TypeResult()
        data class Success(val type: String) : TypeResult()
        data class Failure(val error: Throwable) : TypeResult()
    }
    sealed class DistrictResult : EditRealtyResult() {
        object Processing : DistrictResult()
        data class Success(val district: String) : DistrictResult()
        data class Failure(val error: Throwable) : DistrictResult()
    }
    sealed class DescriptionResult : EditRealtyResult() {
        object Processing : DescriptionResult()
        data class Success(val description: String) : DescriptionResult()
        data class Failure(val error: Throwable) : DescriptionResult()
    }
    sealed class SurfaceResult : EditRealtyResult() {
        object Processing : SurfaceResult()
        data class Success(val surface: String) : SurfaceResult()
        data class Failure(val error: Throwable) : SurfaceResult()
    }
    sealed class NumberOfRoomsResult : EditRealtyResult() {
        object Processing : NumberOfRoomsResult()
        data class Success(val nbRooms: String) : NumberOfRoomsResult()
        data class Failure(val error: Throwable) : NumberOfRoomsResult()
    }
    sealed class NumberOfBathroomsResult : EditRealtyResult() {
        object Processing : NumberOfBathroomsResult()
        data class Success(val nbBathrooms: String) : NumberOfBathroomsResult()
        data class Failure(val error: Throwable) : NumberOfBathroomsResult()
    }
    sealed class NumberOfBedroomsResult : EditRealtyResult() {
        object Processing : NumberOfBedroomsResult()
        data class Success(val nbBedrooms: String) : NumberOfBedroomsResult()
        data class Failure(val error: Throwable) : NumberOfBedroomsResult()
    }
    sealed class AddressResult : EditRealtyResult() {
        object Processing : AddressResult()
        data class Success(val address: String) : AddressResult()
        data class Failure(val error: Throwable) : AddressResult()
    }
    sealed class LoadRealtyResult : EditRealtyResult() {
        object Processing : LoadRealtyResult()
        data class Success(val realty: Realty) : LoadRealtyResult()
        data class Failure(val error: Throwable) : LoadRealtyResult()
    }
    sealed class SaveResult : EditRealtyResult() {
        object Processing : SaveResult()
        object Success : SaveResult()
        data class Failure(val error: Throwable) : SaveResult()
    }
}