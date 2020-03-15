package com.openclassrooms.realestatemanager.feature.details

import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import com.openclassrooms.realestatemanager.feature.details.DetailsAction.LoadRealtyDetailsAction
import com.openclassrooms.realestatemanager.feature.details.DetailsIntent.LoadRealtyDetailsIntent
import com.openclassrooms.realestatemanager.feature.details.DetailsResult.LoadRealtyDetailsResult
import com.openclassrooms.realestatemanager.mvibase.MviViewModel
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class DetailsViewModel(
        private val actionProcessorHolder: DetailsProcessorHolder
) : ViewModel(), MviViewModel<DetailsIntent, DetailsViewState>{

    private val intentsRelay : BehaviorRelay<DetailsIntent> =
            BehaviorRelay.create()

    override fun processIntents(intents: Observable<DetailsIntent>) {
        intents.subscribe(intentsRelay)
    }

    override fun states(): Observable<DetailsViewState> =
            intentsRelay
                .map(::actionFromIntent)
                .compose(actionProcessorHolder.actionProcessor)
                .scan(DetailsViewState.idle(), reducer)
                .distinctUntilChanged()

    private fun actionFromIntent(intent: DetailsIntent) : DetailsAction {
        return when (intent) {
            is LoadRealtyDetailsIntent -> LoadRealtyDetailsAction(intent.realtyId)
        }
    }

    companion object {
        private val reducer = BiFunction { previousState: DetailsViewState, result: DetailsResult ->
            when (result) {
                is LoadRealtyDetailsResult -> when (result) {
                    is LoadRealtyDetailsResult.Success -> previousState.copy(isLoading = false, realty = result.realty)
                    is LoadRealtyDetailsResult.Failure -> previousState.copy(isLoading = false, error = result.error)
                    is LoadRealtyDetailsResult.Loading -> previousState.copy(isLoading = true)
                }
            }

        }
    }

}