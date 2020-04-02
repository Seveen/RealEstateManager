package com.openclassrooms.realestatemanager.app

import androidx.room.Room
import com.openclassrooms.realestatemanager.data.repository.RealtyRepository
import com.openclassrooms.realestatemanager.data.repository.RoomRepository
import com.openclassrooms.realestatemanager.data.room.RealtyDatabase
import com.openclassrooms.realestatemanager.data.service.GeocodingClient
import com.openclassrooms.realestatemanager.feature.allrealty.AllRealtyViewModel
import com.openclassrooms.realestatemanager.feature.details.DetailsViewModel
import com.openclassrooms.realestatemanager.feature.editrealty.EditRealtyViewModel
import com.openclassrooms.realestatemanager.feature.mainactivity.MainActivityViewModel
import com.openclassrooms.realestatemanager.feature.map.MapViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repoModule = module {
    single {
        Room.databaseBuilder(
                get(),
                RealtyDatabase::class.java, "realty_database"
        ).build()}
    single { GeocodingClient() }
    single { get<RealtyDatabase>().realtyDao() }
    single<RealtyRepository> { RoomRepository(get(), get()) }
//    single<RealtyRepository> { DebugRepository() }
}

val mainModule = module {
    viewModel { MainActivityViewModel(get()) }
}

val allRealtyModule = module {
    viewModel { AllRealtyViewModel(get()) }
}

val detailsModule = module {
    viewModel { DetailsViewModel(get()) }
}

val editRealtyModule = module {
    viewModel { EditRealtyViewModel(get()) }
}

val mapModule = module {
    viewModel { MapViewModel(get()) }
}