package com.openclassrooms.realestatemanager.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import java.util.*

@Entity(tableName = "realty_table")
data class Realty(
        @PrimaryKey(autoGenerate = true) val id: Int,
        val type: String,
        val priceInDollars: Double,
        val surface: Double,
        val numberOfRooms: Int,
        val numberOfBathrooms: Int,
        val numberOfBedrooms: Int,
        val district: String,
        val address: String,
        @Embedded val pointsOfInterest: PointsOfInterest,
        val isSold: Boolean,
        val marketEntryDate: Date,
        val saleDate: Date?,
        @Embedded val assignedEstateAgent: EstateAgent,
        val description: String,
        val photos: List<Photo>,
        val location: LatLng?
) {
    companion object {
        fun default() = Realty(0,
                "",
                0.0,
                0.0,
                0,
                0,
                0,
                "",
                "",
                PointsOfInterest.default(),
                false,
                Date(),
                null,
                EstateAgent.default(),
                "",
                emptyList(),
                null)
    }
}