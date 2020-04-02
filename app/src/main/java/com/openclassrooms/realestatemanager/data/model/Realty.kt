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
        val isCloseToMetro: Boolean,
        val isCloseToShops: Boolean,
        val isCloseToPark: Boolean,
        val isSold: Boolean,
        val marketEntryDate: Date,
        val saleDate: Date?,
        @Embedded val assignedEstateAgent: EstateAgent,
        val description: String,
        val photos: List<Photo>,
        val location: LatLng?
) {
    companion object {
        fun default() = Realty(id = 0,
                type = "",
                priceInDollars = 0.0,
                surface = 0.0,
                numberOfRooms = 0,
                numberOfBathrooms = 0,
                numberOfBedrooms = 0,
                district = "",
                address = "",
                isCloseToMetro = false,
                isCloseToShops = false,
                isCloseToPark = false,
                isSold = false,
                marketEntryDate = Date(),
                saleDate = null,
                assignedEstateAgent = EstateAgent.default(),
                description = "",
                photos = emptyList(),
                location = null)
    }
}