package com.openclassrooms.realestatemanager.feature.allrealty

import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import com.openclassrooms.realestatemanager.feature.allrealty.AllRealtyAction.LoadAllRealtyAction
import com.openclassrooms.realestatemanager.feature.allrealty.AllRealtyAction.NavigateToDetailsAction
import com.openclassrooms.realestatemanager.feature.allrealty.AllRealtyIntent.LoadAllRealtyIntent
import com.openclassrooms.realestatemanager.feature.allrealty.AllRealtyIntent.NavigateToDetailsIntent
import com.openclassrooms.realestatemanager.feature.allrealty.AllRealtyResult.LoadAllRealtyResult
import com.openclassrooms.realestatemanager.feature.allrealty.AllRealtyResult.NavigateToDetailsResult
import com.openclassrooms.realestatemanager.mvibase.MviViewModel
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class AllRealtyViewModel(
        private val actionProcessorHolder: AllRealtyProcessorHolder
) : ViewModel(), MviViewModel<AllRealtyIntent, AllRealtyViewState> {

    private val intentsRelay : BehaviorRelay<AllRealtyIntent> =
            BehaviorRelay.create()

    override fun processIntents(intents: Observable<AllRealtyIntent>) {
        intents.subscribe(intentsRelay)
    }

    override fun states(): Observable<AllRealtyViewState> = compose()


    private fun compose(): Observable<AllRealtyViewState> {
        return intentsRelay
                .map(::actionFromIntent)
                .compose(actionProcessorHolder.actionProcessor)
                .scan(AllRealtyViewState.idle(), reducer)
                .distinctUntilChanged()
    }

    private fun actionFromIntent(intent: AllRealtyIntent) : AllRealtyAction {
        return when (intent) {
            is LoadAllRealtyIntent -> LoadAllRealtyAction
            is NavigateToDetailsIntent -> NavigateToDetailsAction(intent.realty)
        }
    }

    companion object {
        private val reducer = BiFunction { previousState: AllRealtyViewState, result: AllRealtyResult ->
            when (result) {
                is LoadAllRealtyResult -> when (result) {
                    is LoadAllRealtyResult.Success -> previousState.copy(isLoading = false, realty = result.realty)
                    is LoadAllRealtyResult.Failure -> previousState.copy(isLoading = false, error = result.error)
                    is LoadAllRealtyResult.Loading -> previousState.copy(isLoading = true)
                }
                is NavigateToDetailsResult -> when (result) {
                    is NavigateToDetailsResult.Success -> previousState
                    is NavigateToDetailsResult.Failure -> previousState.copy(isLoading = false, error = result.error)
                }
            }
        }
    }
}