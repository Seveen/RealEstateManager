package com.openclassrooms.realestatemanager.data.repository

import com.openclassrooms.realestatemanager.data.service.LocationService
import kotlinx.coroutines.flow.distinctUntilChanged

class LocationRepository(private val locationService: LocationService) {
    fun getLocation(interval: Long, priority: Int) = locationService.requestLocation(interval, priority)
            .distinctUntilChanged { old, new ->
                old.altitude == new.altitude
                        && old.longitude == new.longitude
                        && old.latitude == new.latitude
            }
}