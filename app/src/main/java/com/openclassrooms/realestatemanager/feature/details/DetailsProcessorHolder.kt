package com.openclassrooms.realestatemanager.feature.details

import com.openclassrooms.realestatemanager.app.scheduler.BaseSchedulerProvider
import com.openclassrooms.realestatemanager.data.repository.RealtyRepository
import com.openclassrooms.realestatemanager.feature.details.DetailsAction.LoadRealtyDetailsAction
import com.openclassrooms.realestatemanager.feature.details.DetailsResult.LoadRealtyDetailsResult
import com.openclassrooms.realestatemanager.utils.notOfType
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class DetailsProcessorHolder(
        private val realtyRepository: RealtyRepository,
        private val schedulerProvider: BaseSchedulerProvider
) {

    private val loadRealtyDetailsProcessor =
            ObservableTransformer<LoadRealtyDetailsAction, LoadRealtyDetailsResult> { actions ->
                actions.flatMap {
                    realtyRepository.getRealtyById(it.realtyId)
                            .map { result -> LoadRealtyDetailsResult.Success(result) }
                            .cast(LoadRealtyDetailsResult::class.java)
                            .onErrorReturn(LoadRealtyDetailsResult::Failure)
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                            .startWith(LoadRealtyDetailsResult.Loading)
                }
            }

    private val identityProcessor =
            ObservableTransformer<DetailsAction, DetailsResult> { actions ->
                actions.cast(DetailsResult::class.java)
            }

    internal var actionProcessor =
            ObservableTransformer<DetailsAction, DetailsResult> { actions ->
                actions.publish { shared ->
                    Observable.merge(
                            shared.ofType(LoadRealtyDetailsAction::class.java).compose(loadRealtyDetailsProcessor),
                            shared.notOfType(LoadRealtyDetailsAction::class.java).compose(identityProcessor)
                    )
                }
            }
}