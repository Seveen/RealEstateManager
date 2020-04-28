package com.openclassrooms.realestatemanager.app

import androidx.multidex.MultiDexApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RealEstateManagerApplication : MultiDexApplication() {

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
                    addAgentModule,
                    loanCalculatorModule
            )
        }
    }
}