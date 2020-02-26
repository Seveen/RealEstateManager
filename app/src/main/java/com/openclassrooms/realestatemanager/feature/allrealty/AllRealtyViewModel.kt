package com.openclassrooms.realestatemanager.feature.allrealty

import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.PublishRelay
import com.openclassrooms.realestatemanager.feature.allrealty.AllRealtyAction.*
import com.openclassrooms.realestatemanager.feature.allrealty.AllRealtyIntent.*
import com.openclassrooms.realestatemanager.feature.allrealty.AllRealtyResult.*
import com.openclassrooms.realestatemanager.mvibase.MviViewModel
import com.openclassrooms.realestatemanager.utils.notOfType
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction

class AllRealtyViewModel(
        private val actionProcessorHolder: AllRealtyProcessorHolder
) : ViewModel(), MviViewModel<AllRealtyIntent, AllRealtyViewState> {

    private val intentsRelay : PublishRelay<AllRealtyIntent> = PublishRelay.create()
    private val statesObservable: Observable<AllRealtyViewState> = compose()

    private val intentFilter = ObservableTransformer<AllRealtyIntent, AllRealtyIntent> { intents ->
            intents.publish { shared ->
            Observable.merge(
                    shared.ofType(LoadAllRealtyIntent::class.java).take(1),
                    shared.notOfType(LoadAllRealtyIntent::class.java)
            )
        }
    }

    override fun processIntents(intents: Observable<AllRealtyIntent>) {
        intents.subscribe(intentsRelay)
    }

    override fun states(): Observable<AllRealtyViewState> = statesObservable

    private fun compose(): Observable<AllRealtyViewState> {
        return intentsRelay
                .compose(intentFilter)
                .map(::actionFromIntent)
                .compose(actionProcessorHolder.actionProcessor)
                .scan(AllRealtyViewState.idle(), reducer)
                .distinctUntilChanged()
                .replay(1)
                .autoConnect(0)
    }

    private fun actionFromIntent(intent: AllRealtyIntent) : AllRealtyAction {
        return when (intent) {
            is LoadAllRealtyIntent -> LoadAllRealtyAction
            is ClearAllRealtyIntent -> ClearAllRealtyAction
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
                is ClearAllRealtyResult -> when (result) {
                    is ClearAllRealtyResult.Success -> previousState.copy(isLoading = false, realty = emptyList())
                    is ClearAllRealtyResult.Failure -> previousState.copy(isLoading = false, error = result.error)
                    is ClearAllRealtyResult.Clearing -> previousState.copy(isLoading = true)
                }
            }
        }
    }
}