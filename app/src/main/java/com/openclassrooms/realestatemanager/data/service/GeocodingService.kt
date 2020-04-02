package com.openclassrooms.realestatemanager.data.service

import com.openclassrooms.realestatemanager.data.dto.GeocodingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingService {

    @GET("maps/api/geocode/json")
    suspend fun getGeocoding(@Query("address") address: String,
                             @Query("key") key: String) : GeocodingResponse

}