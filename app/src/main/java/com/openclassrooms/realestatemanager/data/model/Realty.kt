package com.openclassrooms.realestatemanager.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "realty_table")
data class Realty(
        @PrimaryKey val id: String,
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
        val photos: List<Photo>
) {
    companion object {
        fun default() = Realty("",
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
                emptyList())
    }
}