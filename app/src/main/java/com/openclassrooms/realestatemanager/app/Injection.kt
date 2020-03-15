package com.openclassrooms.realestatemanager.app

import android.content.Context
import com.openclassrooms.realestatemanager.app.scheduler.BaseSchedulerProvider
import com.openclassrooms.realestatemanager.app.scheduler.SchedulerProvider
import com.openclassrooms.realestatemanager.data.repository.RealtyRepository
import com.openclassrooms.realestatemanager.data.repository.room.DebugRepository

object Injection {
    fun provideRealtyRepository(context: Context): RealtyRepository = DebugRepository()
    fun provideSchedulerProvider(): BaseSchedulerProvider = SchedulerProvider
}