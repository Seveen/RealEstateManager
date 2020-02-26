package com.openclassrooms.realestatemanager.feature.allrealty

import com.openclassrooms.realestatemanager.app.scheduler.BaseSchedulerProvider
import com.openclassrooms.realestatemanager.data.repository.RealtyRepository
import com.openclassrooms.realestatemanager.feature.allrealty.AllRealtyAction.*
import com.openclassrooms.realestatemanager.feature.allrealty.AllRealtyResult.*
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

    private val clearAllRealtyProcessor =
            ObservableTransformer<ClearAllRealtyAction, ClearAllRealtyResult> { actions ->
                actions.flatMap {
                    realtyRepository.clearAllRealty()
                            .map { ClearAllRealtyResult.Success }
                            .cast(ClearAllRealtyResult::class.java)
                            .onErrorReturn(ClearAllRealtyResult::Failure)
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                            .startWith(ClearAllRealtyResult.Clearing)
                }
            }

    internal var actionProcessor =
            ObservableTransformer<AllRealtyAction, AllRealtyResult> { actions ->
                actions.publish { shared ->
                    Observable.merge(
                            shared.ofType(LoadAllRealtyAction::class.java).compose(loadAllRealtyProcessor),
                            shared.ofType(ClearAllRealtyAction::class.java).compose(clearAllRealtyProcessor)
                    )
                }
            }
}