package com.openclassrooms.realestatemanager.feature.editrealty

import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import com.openclassrooms.realestatemanager.feature.editrealty.EditRealtyAction.*
import com.openclassrooms.realestatemanager.feature.editrealty.EditRealtyIntent.*
import com.openclassrooms.realestatemanager.feature.editrealty.EditRealtyResult.*
import com.openclassrooms.realestatemanager.mvibase.MviViewModel
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class EditRealtyViewModel(
        private val actionProcessorHolder: EditRealtyProcessorHolder
) : ViewModel(), MviViewModel<EditRealtyIntent, EditRealtyViewState> {

    private val intentsRelay : BehaviorRelay<EditRealtyIntent> = BehaviorRelay.create()

    override fun processIntents(intents: Observable<EditRealtyIntent>) {
        intents.subscribe(intentsRelay)
    }

    override fun states(): Observable<EditRealtyViewState> =
        intentsRelay
                .map(::actionFromIntent)
                .compose(actionProcessorHolder.actionProcessor)
                .scan(EditRealtyViewState.default(), reducer)
                .distinctUntilChanged()

    private fun actionFromIntent(intent: EditRealtyIntent) : EditRealtyAction {
        return when (intent) {
            is TypeIntent -> TypeAction(intent.type)
            is DistrictIntent -> DistrictAction(intent.district)
            is DescriptionIntent -> DescriptionAction(intent.description)
            is SurfaceIntent -> SurfaceAction(intent.surface)
            is NumberOfRoomsIntent -> NumberOfRoomsAction(intent.nbRooms)
            is NumberOfBathroomsIntent -> NumberOfBathroomsAction(intent.nbBathrooms)
            is NumberOfBedroomsIntent -> NumberOfBedroomsAction(intent.nbBedrooms)
            is AddressIntent -> AddressAction(intent.address)
            is LoadRealtyIntent -> LoadRealtyAction(intent.realtyId)
            is SaveIntent -> SaveAction(intent.realty)
        }
    }

    companion object {
        private val reducer = BiFunction { previousState : EditRealtyViewState, result: EditRealtyResult ->
            when (result) {
                is TypeResult -> when (result) {
                    is TypeResult.Processing -> previousState.copy(isProcessing = true, error = null)
                    is TypeResult.Success -> previousState.copy(isProcessing = false, realty = previousState.realty.copy(type = result.type))
                    is TypeResult.Failure -> previousState.copy(isProcessing = false, error = result.error)
                }
                is DistrictResult -> when (result) {
                    is DistrictResult.Processing -> previousState.copy(isProcessing = true, error = null)
                    is DistrictResult.Success -> previousState.copy(isProcessing = false, realty = previousState.realty.copy(district = result.district))
                    is DistrictResult.Failure -> previousState.copy(isProcessing = false, error = result.error)
                }
                is DescriptionResult -> when (result) {
                    is DescriptionResult.Processing -> previousState.copy(isProcessing = true, error = null)
                    is DescriptionResult.Success -> previousState.copy(isProcessing = false, realty = previousState.realty.copy(description = result.description))
                    is DescriptionResult.Failure -> previousState.copy(isProcessing = false, error = result.error)
                }
                is SurfaceResult -> when (result) {
                    is SurfaceResult.Processing -> previousState.copy(isProcessing = true, error = null)
                    is SurfaceResult.Success -> previousState.copy(isProcessing = false, realty = previousState.realty.copy(surface = result.surface.toDoubleOrNull() ?: 0.0))
                    is SurfaceResult.Failure -> previousState.copy(isProcessing = false, error = result.error)
                }
                is NumberOfRoomsResult -> when (result) {
                    is NumberOfRoomsResult.Processing -> previousState.copy(isProcessing = true, error = null)
                    is NumberOfRoomsResult.Success -> previousState.copy(isProcessing = false, realty = previousState.realty.copy(numberOfRooms = result.nbRooms.toIntOrNull() ?: 0))
                    is NumberOfRoomsResult.Failure -> previousState.copy(isProcessing = false, error = result.error)
                }
                is NumberOfBathroomsResult -> when (result) {
                    is NumberOfBathroomsResult.Processing -> previousState.copy(isProcessing = true, error = null)
                    is NumberOfBathroomsResult.Success -> previousState.copy(isProcessing = false, realty = previousState.realty.copy(numberOfBathrooms = result.nbBathrooms.toIntOrNull() ?: 0))
                    is NumberOfBathroomsResult.Failure -> previousState.copy(isProcessing = false, error = result.error)
                }
                is NumberOfBedroomsResult -> when (result) {
                    is NumberOfBedroomsResult.Processing -> previousState.copy(isProcessing = true, error = null)
                    is NumberOfBedroomsResult.Success -> previousState.copy(isProcessing = false, realty = previousState.realty.copy(numberOfBedrooms = result.nbBedrooms.toIntOrNull() ?: 0))
                    is NumberOfBedroomsResult.Failure -> previousState.copy(isProcessing = false, error = result.error)
                }
                is AddressResult -> when (result) {
                    is AddressResult.Processing -> previousState.copy(isProcessing = true, error = null)
                    is AddressResult.Success -> previousState.copy(isProcessing = false, realty = previousState.realty.copy(address = result.address))
                    is AddressResult.Failure -> previousState.copy(isProcessing = false, error = result.error)
                }
                is LoadRealtyResult -> when (result) {
                    is LoadRealtyResult.Processing -> previousState.copy(isProcessing = true, error = null)
                    is LoadRealtyResult.Success -> previousState.copy(isProcessing = false, realty = result.realty)
                    is LoadRealtyResult.Failure -> previousState.copy(isProcessing = false, error = result.error)
                }
                is SaveResult -> when (result) {
                    is SaveResult.Processing -> previousState.copy(isProcessing = true, error = null)
                    is SaveResult.Success -> previousState.copy(isProcessing = false, error = null, isSaveComplete = true)
                    is SaveResult.Failure -> previousState.copy(isProcessing = false, error = result.error)
                }
            }
        }
    }
}