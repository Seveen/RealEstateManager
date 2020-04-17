package com.openclassrooms.realestatemanager.data.model

import android.content.ContentValues
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.data.database.Converters
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
        val isCloseToSubway: Boolean,
        val isCloseToShops: Boolean,
        val isCloseToPark: Boolean,
        val isSold: Boolean,
        val marketEntryDate: Date,
        val saleDate: Date?,
        @ForeignKey(entity = EstateAgent::class,
                parentColumns = ["id"],
                childColumns = ["id"]) val assignedEstateAgentId: Int,
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
                isCloseToSubway = false,
                isCloseToShops = false,
                isCloseToPark = false,
                isSold = false,
                marketEntryDate = Date(),
                saleDate = null,
                assignedEstateAgentId = 0,
                description = "",
                photos = emptyList(),
                location = null)

        fun fromContentValues(values: ContentValues): Realty {
            val id = if (values.containsKey("id")) values.getAsInteger("id") else 0
            val type = if (values.containsKey("type")) values.getAsString("type") else ""
            val priceInDollars = if (values.containsKey("priceInDollars")) values.getAsDouble("priceInDollars") else 0.0
            val surface = if (values.containsKey("surface")) values.getAsDouble("surface") else 0.0
            val numberOfRooms = if (values.containsKey("numberOfRooms")) values.getAsInteger("numberOfRooms") else 0
            val numberOfBathrooms = if (values.containsKey("numberOfBathrooms")) values.getAsInteger("numberOfBathrooms") else 0
            val numberOfBedrooms = if (values.containsKey("numberOfBedrooms")) values.getAsInteger("numberOfBedrooms") else 0
            val district = if (values.containsKey("type")) values.getAsString("type") else ""
            val address = if (values.containsKey("type")) values.getAsString("type") else ""
            val isCloseToSubway = if (values.containsKey("isCloseToSubway")) values.getAsBoolean("isCloseToSubway") else false
            val isCloseToShops = if (values.containsKey("isCloseToShops")) values.getAsBoolean("isCloseToShops") else false
            val isCloseToPark = if (values.containsKey("isCloseToPark")) values.getAsBoolean("isCloseToPark") else false
            val isSold = if (values.containsKey("isSold")) values.getAsBoolean("isSold") else false
            val marketEntryDate = if (values.containsKey("marketEntryDate")) values.getAsLong("marketEntryDate") else 0
            val saleDate = if (values.containsKey("saleDate")) values.getAsLong("saleDate") else 0
            val assignedEstateAgentId = if (values.containsKey("assignedEstateAgentId")) values.getAsInteger("assignedEstateAgentId") else 0
            val description = if (values.containsKey("description")) values.getAsString("description") else ""
            val photos = if (values.containsKey("photos")) values.getAsString("photos") else ""
            val location = if (values.containsKey("location")) values.getAsString("location") else ""

            return Realty(id, type, priceInDollars, surface, numberOfRooms, numberOfBathrooms,
                    numberOfBedrooms, district, address, isCloseToSubway, isCloseToShops, isCloseToPark, isSold,
                    Date(marketEntryDate), Date(saleDate), assignedEstateAgentId, description, Converters().deserializePhotos(photos)?: emptyList(), Converters().deserializeLatLng(location))
        }
    }
}