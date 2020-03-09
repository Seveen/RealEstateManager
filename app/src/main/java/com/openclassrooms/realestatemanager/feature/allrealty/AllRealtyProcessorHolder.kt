package com.openclassrooms.realestatemanager.feature.allrealty

import com.openclassrooms.realestatemanager.app.scheduler.BaseSchedulerProvider
import com.openclassrooms.realestatemanager.data.repository.RealtyRepository
import com.openclassrooms.realestatemanager.feature.allrealty.AllRealtyAction.LoadAllRealtyAction
import com.openclassrooms.realestatemanager.feature.allrealty.AllRealtyAction.NavigateToDetailsAction
import com.openclassrooms.realestatemanager.feature.allrealty.AllRealtyResult.LoadAllRealtyResult
import com.openclassrooms.realestatemanager.feature.allrealty.AllRealtyResult.NavigateToDetailsResult
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class AllRealtyProcessorHolder(
        private val realtyRepository: RealtyRepository,
        private val schedulerProvider: BaseSchedulerProvider
) {

    private val loadAllRealtyProcessor =
            ObservableTransformer<LoadAllRealtyAction, LoadAllRealtyResult> { actions ->
                actions.flatMap {
                    realtyRepository.getAllRealty()
                            .map { realty -> LoadAllRealtyResult.Success(realty) }
                            .cast(LoadAllRealtyResult::class.java)
                            .onErrorReturn(LoadAllRealtyResult::Failure)
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                            .startWith(LoadAllRealtyResult.Loading)
                }
            }

    private val navigateToDetailsProcessor =
            ObservableTransformer<NavigateToDetailsAction, NavigateToDetailsResult> { actions ->
                actions.flatMap {
                    realtyRepository.setCurrentDetailsRealty(it.realty)
                            .map { NavigateToDetailsResult.Success }
                            .cast(NavigateToDetailsResult::class.java)
                            .onErrorReturn(NavigateToDetailsResult::Failure)
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                }
            }

    internal var actionProcessor =
            ObservableTransformer<AllRealtyAction, AllRealtyResult> { actions ->
                actions.publish { shared ->
                    Observable.merge(
                            shared.ofType(LoadAllRealtyAction::class.java).compose(loadAllRealtyProcessor),
                            shared.ofType(NavigateToDetailsAction::class.java).compose(navigateToDetailsProcessor)
                    )
                }
            }
}