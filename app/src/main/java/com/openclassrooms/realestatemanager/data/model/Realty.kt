package com.openclassrooms.realestatemanager.data.model

import java.util.*

data class Realty(
        val type: String,
        val priceInDollars: Double,
        val area: Double,
        val numberOfRooms: Int,
        val description: String,
        val photos: List<String>, //TODO: probably not strings
        val address: String,
        val pointsOfInterest: List<String>, //TODO: probably not strings
        val isSold: Boolean,
        val marketEntryDate: Date,
        val saleDate: Date,
        val assignedEstateAgent: String //TODO: probably not a string
)