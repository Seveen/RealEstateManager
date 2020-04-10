package com.openclassrooms.realestatemanager.data.repository

import com.openclassrooms.realestatemanager.data.service.LocationService

class LocationRepository(private val locationService: LocationService) {
    fun getLocation(interval: Long, priority: Int) = locationService.requestLocation(interval, priority)
}