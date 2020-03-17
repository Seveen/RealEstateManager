package com.openclassrooms.realestatemanager.feature.editrealty

import com.openclassrooms.realestatemanager.app.scheduler.BaseSchedulerProvider
import com.openclassrooms.realestatemanager.data.repository.RealtyRepository
import com.openclassrooms.realestatemanager.feature.editrealty.EditRealtyAction.*
import com.openclassrooms.realestatemanager.feature.editrealty.EditRealtyResult.*
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

class EditRealtyProcessorHolder(
        private val realtyRepository: RealtyRepository,
        private val schedulerProvider: BaseSchedulerProvider
) {

    private val typeProcessor =
            ObservableTransformer<TypeAction, TypeResult> { actions ->
                actions
                        .map { action -> TypeResult.Success(action.type) }
                        .cast(TypeResult::class.java)
                        .onErrorReturn(TypeResult::Failure)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .startWith(TypeResult.Processing)
            }

    private val districtProcessor =
            ObservableTransformer<DistrictAction, DistrictResult> { actions ->
                actions
                        .map { action -> DistrictResult.Success(action.district) }
                        .cast(DistrictResult::class.java)
                        .onErrorReturn(DistrictResult::Failure)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .startWith(DistrictResult.Processing)
            }

    private val descriptionProcessor =
            ObservableTransformer<DescriptionAction, DescriptionResult> { actions ->
                actions
                        .map { action -> DescriptionResult.Success(action.description) }
                        .cast(DescriptionResult::class.java)
                        .onErrorReturn(DescriptionResult::Failure)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .startWith(DescriptionResult.Processing)
            }

    private val surfaceProcessor =
            ObservableTransformer<SurfaceAction, SurfaceResult> { actions ->
                actions
                        .map { action -> SurfaceResult.Success(action.surface) }
                        .cast(SurfaceResult::class.java)
                        .onErrorReturn(SurfaceResult::Failure)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .startWith(SurfaceResult.Processing)
            }

    private val numberOfRoomsProcessor =
            ObservableTransformer<NumberOfRoomsAction, NumberOfRoomsResult> { actions ->
                actions
                        .map { action -> NumberOfRoomsResult.Success(action.nbRooms) }
                        .cast(NumberOfRoomsResult::class.java)
                        .onErrorReturn(NumberOfRoomsResult::Failure)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .startWith(NumberOfRoomsResult.Processing)
            }

    private val numberOfBathroomsProcessor =
            ObservableTransformer<NumberOfBathroomsAction, NumberOfBathroomsResult> { actions ->
                actions
                        .map { action -> NumberOfBathroomsResult.Success(action.nbBathrooms) }
                        .cast(NumberOfBathroomsResult::class.java)
                        .onErrorReturn(NumberOfBathroomsResult::Failure)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .startWith(NumberOfBathroomsResult.Processing)
            }

    private val numberOfBedroomsProcessor =
            ObservableTransformer<NumberOfBedroomsAction, NumberOfBedroomsResult> { actions ->
                actions
                        .map { action -> NumberOfBedroomsResult.Success(action.nbBedrooms) }
                        .cast(NumberOfBedroomsResult::class.java)
                        .onErrorReturn(NumberOfBedroomsResult::Failure)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .startWith(NumberOfBedroomsResult.Processing)
            }

    private val addressProcessor =
            ObservableTransformer<AddressAction, AddressResult> { actions ->
                actions
                        .map { action -> AddressResult.Success(action.address) }
                        .cast(AddressResult::class.java)
                        .onErrorReturn(AddressResult::Failure)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .startWith(AddressResult.Processing)
            }

    private val loadRealtyProcessor =
            ObservableTransformer<LoadRealtyAction, LoadRealtyResult> { actions ->
                actions.flatMap {
                    realtyRepository.getRealtyById(it.realtyId)
                            .map { realty -> LoadRealtyResult.Success(realty) }
                            .cast(LoadRealtyResult::class.java)
                            .onErrorReturn(LoadRealtyResult::Failure)
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                            .startWith(LoadRealtyResult.Processing)
                }
            }

    private val saveProcessor =
            ObservableTransformer<SaveAction, SaveResult> { actions ->
                actions.flatMap { action ->
                    realtyRepository.saveRealty(action.realty)
                            .map { SaveResult.Success }
                            .cast(SaveResult::class.java)
                            .onErrorReturn(SaveResult::Failure)
                            .subscribeOn(schedulerProvider.io())
                            .observeOn(schedulerProvider.ui())
                            .startWith(SaveResult.Processing)
                }
            }

    internal var actionProcessor =
            ObservableTransformer<EditRealtyAction, EditRealtyResult> { actions ->
                actions.publish { shared ->
                    Observable.merge(
                            shared.ofType(TypeAction::class.java).compose(typeProcessor),
                            shared.ofType(DistrictAction::class.java).compose(districtProcessor),
                            shared.ofType(DescriptionAction::class.java).compose(descriptionProcessor),
                            shared.ofType(SurfaceAction::class.java).compose(surfaceProcessor))
                    .mergeWith(shared.ofType(NumberOfRoomsAction::class.java).compose(numberOfRoomsProcessor))
                    .mergeWith(shared.ofType(NumberOfBathroomsAction::class.java).compose(numberOfBathroomsProcessor))
                    .mergeWith(shared.ofType(NumberOfBedroomsAction::class.java).compose(numberOfBedroomsProcessor))
                    .mergeWith(shared.ofType(AddressAction::class.java).compose(addressProcessor))
                    .mergeWith(shared.ofType(LoadRealtyAction::class.java).compose(loadRealtyProcessor))
                    .mergeWith(shared.ofType(SaveAction::class.java).compose(saveProcessor))
                }
            }
}