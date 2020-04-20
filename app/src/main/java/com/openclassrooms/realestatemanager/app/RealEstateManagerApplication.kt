package com.openclassrooms.realestatemanager.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RealEstateManagerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@RealEstateManagerApplication)
            modules(
                    mainModule,
                    repoModule,
                    allRealtyModule,
                    detailsModule,
                    editRealtyModule,
                    mapModule,
                    searchModule,
                    addAgentModule
            )
        }
    }
}