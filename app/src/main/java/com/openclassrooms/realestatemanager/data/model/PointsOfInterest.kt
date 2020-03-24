package com.openclassrooms.realestatemanager.data.model

import androidx.room.ColumnInfo

data class PointsOfInterest(
        @ColumnInfo(name = "poi_id") var id: Int,
        var closeToMetro: Boolean,
        var closeToShops: Boolean
) {
    companion object {
        fun default() = PointsOfInterest(0, closeToMetro = false, closeToShops = false)
    }
}