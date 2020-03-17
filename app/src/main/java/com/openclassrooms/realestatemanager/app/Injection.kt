package com.openclassrooms.realestatemanager.app

import com.openclassrooms.realestatemanager.app.scheduler.BaseSchedulerProvider
import com.openclassrooms.realestatemanager.app.scheduler.SchedulerProvider
import com.openclassrooms.realestatemanager.data.repository.RealtyRepository
import com.openclassrooms.realestatemanager.data.repository.room.DebugRepository
import com.openclassrooms.realestatemanager.feature.allrealty.AllRealtyProcessorHolder
import com.openclassrooms.realestatemanager.feature.allrealty.AllRealtyViewModel
import com.openclassrooms.realestatemanager.feature.details.DetailsProcessorHolder
import com.openclassrooms.realestatemanager.feature.details.DetailsViewModel
import com.openclassrooms.realestatemanager.feature.editrealty.EditRealtyProcessorHolder
import com.openclassrooms.realestatemanager.feature.editrealty.EditRealtyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repoModule = module {
    single<RealtyRepository> { DebugRepository() }
    single<BaseSchedulerProvider> { SchedulerProvider }
}

val allRealtyModule = module {
    single { AllRealtyProcessorHolder(get(), get()) }
    viewModel { AllRealtyViewModel(get()) }
}

val detailsModule = module {
    single { DetailsProcessorHolder(get(), get()) }
    viewModel { DetailsViewModel(get()) }
}

val editRealtyModule = module {
    single { EditRealtyProcessorHolder(get(), get()) }
    viewModel { EditRealtyViewModel(get()) }
}