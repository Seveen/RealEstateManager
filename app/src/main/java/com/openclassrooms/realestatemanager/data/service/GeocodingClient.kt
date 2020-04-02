package com.openclassrooms.realestatemanager.data.service

import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GeocodingClient {
    private val geocodingService by lazy {
        Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(GeocodingService::class.java)
    }

    suspend fun getGeocoding(address: String) : LatLng? {
        val results = geocodingService.getGeocoding(address, BuildConfig.PLACES_KEY).results
        val location = results.getOrNull(0)?.geometry?.location
        return location?.let {
            LatLng(it.lat, it.lng)
        }
    }
}