package com.openclassrooms.realestatemanager.app

import androidx.room.Room
import com.openclassrooms.realestatemanager.data.database.RealtyDatabase
import com.openclassrooms.realestatemanager.data.repository.LocationRepository
import com.openclassrooms.realestatemanager.data.repository.RealtyRepository
import com.openclassrooms.realestatemanager.data.repository.RoomRealtyRepository
import com.openclassrooms.realestatemanager.data.service.GeocodingClient
import com.openclassrooms.realestatemanager.data.service.LocationService
import com.openclassrooms.realestatemanager.feature.allrealty.AllRealtyViewModel
import com.openclassrooms.realestatemanager.feature.details.DetailsViewModel
import com.openclassrooms.realestatemanager.feature.editrealty.EditRealtyViewModel
import com.openclassrooms.realestatemanager.feature.mainactivity.MainActivityViewModel
import com.openclassrooms.realestatemanager.feature.map.MapViewModel
import com.openclassrooms.realestatemanager.feature.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repoModule = module {
    single {
        Room.databaseBuilder(
                get(),
                RealtyDatabase::class.java, "realty_database"
        ).build()}
    single { GeocodingClient() }
    single { LocationService(get()) }
    single { get<RealtyDatabase>().realtyDao() }
    single<RealtyRepository> { RoomRealtyRepository(get(), get()) }
    single { LocationRepository(get()) }
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
    viewModel { MapViewModel(get(), get()) }
}

val searchModule = module {
    viewModel { SearchViewModel(get()) }
}