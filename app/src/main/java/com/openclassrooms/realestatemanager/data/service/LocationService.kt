package com.openclassrooms.realestatemanager.data.service

import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class LocationService(context: Context) {

    private val locationClient = LocationServices.getFusedLocationProviderClient(context)

    fun requestLocation(interval: Long, priority: Int): Flow<Location> = callbackFlow {
        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult?) {
                super.onLocationResult(result)
                result?.lastLocation?.let {
                    sendBlocking(it)
                }
            }
        }

        locationClient.requestLocationUpdates(LocationRequest.create().apply {
                this.interval = interval
                this.priority = priority
            }, callback, Looper.getMainLooper())

        awaitClose {
            locationClient.removeLocationUpdates(callback)
        }
    }
}