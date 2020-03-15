package com.openclassrooms.realestatemanager.feature.allrealty

import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.BehaviorRelay
import com.openclassrooms.realestatemanager.feature.allrealty.AllRealtyAction.LoadAllRealtyAction
import com.openclassrooms.realestatemanager.feature.allrealty.AllRealtyIntent.LoadAllRealtyIntent
import com.openclassrooms.realestatemanager.feature.allrealty.AllRealtyResult.LoadAllRealtyResult
import com.openclassrooms.realestatemanager.mvibase.MviViewModel
import com.openclassrooms.realestatemanager.utils.notOfType
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction

class AllRealtyViewModel(
        private val actionProcessorHolder: AllRealtyProcessorHolder
) : ViewModel(), MviViewModel<AllRealtyIntent, AllRealtyViewState> {

    private val intentsRelay : BehaviorRelay<AllRealtyIntent> =
            BehaviorRelay.create()

    private val intentFilter: ObservableTransformer<AllRealtyIntent, AllRealtyIntent>
        get() = ObservableTransformer { intents ->
            intents.publish {shared ->
                Observable.merge(
                        shared.ofType(LoadAllRealtyIntent::class.java).take(1),
                        shared.notOfType(LoadAllRealtyIntent::class.java)
                )
            }
        }

    override fun processIntents(intents: Observable<AllRealtyIntent>) {
        intents.subscribe(intentsRelay)
    }

    override fun states(): Observable<AllRealtyViewState> =
            intentsRelay
                    .compose(intentFilter)
                    .map(::actionFromIntent)
                    .compose(actionProcessorHolder.actionProcessor)
                    .scan(AllRealtyViewState.idle(), reducer)
                    .distinctUntilChanged()

    private fun actionFromIntent(intent: AllRealtyIntent) : AllRealtyAction {
        return when (intent) {
            is LoadAllRealtyIntent -> LoadAllRealtyAction
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
            }
        }
    }
}