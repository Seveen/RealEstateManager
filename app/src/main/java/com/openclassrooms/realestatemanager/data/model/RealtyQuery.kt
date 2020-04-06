package com.openclassrooms.realestatemanager.data.model

//TODO : Add more requests
data class RealtyQuery(
    val types: List<String>,
    val lowestPrice: Double,
    val highestPrice: Double,
    val isCloseToSubway: Boolean,
    val isCloseToShops: Boolean,
    val isCloseToPark: Boolean,
    val isSold: Boolean
) {
    companion object {
        fun default() = RealtyQuery(
                types = listOf(),
                lowestPrice = 0.0,
                highestPrice = Double.MAX_VALUE,
                isCloseToSubway = false,
                isCloseToShops = false,
                isCloseToPark = false,
                isSold = false
        )
    }
}