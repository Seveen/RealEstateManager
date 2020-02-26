package com.openclassrooms.realestatemanager.app

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.feature.allrealty.AllRealtyProcessorHolder
import com.openclassrooms.realestatemanager.feature.allrealty.AllRealtyViewModel
import com.openclassrooms.realestatemanager.utils.SingletonHolder

class ViewModelFactory private constructor(
        private val applicationContext: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == AllRealtyViewModel::class.java) {
            return AllRealtyViewModel(
                    AllRealtyProcessorHolder(
                            Injection.provideRealtyRepository(applicationContext),
                            Injection.provideSchedulerProvider())) as T
        }
        throw IllegalAccessException("unknown model class $modelClass")
    }

    companion object : SingletonHolder<ViewModelFactory, Context>(::ViewModelFactory)
}