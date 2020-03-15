package com.openclassrooms.realestatemanager.feature.allrealty

import com.openclassrooms.realestatemanager.app.scheduler.BaseSchedulerProvider
import com.openclassrooms.realestatemanager.data.repository.RealtyRepository
import com.openclassrooms.realestatemanager.feature.allrealty.AllRealtyAction.LoadAllRealtyAction
import com.openclassrooms.realestatemanager.feature.allrealty.AllRealtyResult.LoadAllRealtyResult
import com.openclassrooms.realestatemanager.utils.notOfType
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

    private val identityProcessor =
            ObservableTransformer<AllRealtyAction, AllRealtyResult> { actions ->
                actions.cast(AllRealtyResult::class.java)
            }

    internal var actionProcessor =
            ObservableTransformer<AllRealtyAction, AllRealtyResult> { actions ->
                actions.publish { shared ->
                    Observable.merge(
                            shared.ofType(LoadAllRealtyAction::class.java).compose(loadAllRealtyProcessor),
                            shared.notOfType(LoadAllRealtyAction::class.java).compose(identityProcessor)
                    )
                }
            }
}