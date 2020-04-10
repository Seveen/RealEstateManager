package com.openclassrooms.realestatemanager.data.model

//TODO : Add more requests
data class RealtyQuery(
    val types: List<String>,
    val lowestPrice: Double?,
    val highestPrice: Double?,
    val isCloseToSubway: Boolean,
    val isCloseToShops: Boolean,
    val isCloseToPark: Boolean,
    val isSold: Boolean
) {
    fun toSQLQuery(): Pair<String, Array<Any>> {
        var queryString = "SELECT * FROM realty_table"
        val bindParameters = arrayListOf<Any>()
        var containsCondition = false

        lowestPrice?.let {
            queryString += " WHERE"
            queryString += " priceInDollars >= ?"
            bindParameters.add(it)
            containsCondition = true
        }

        highestPrice?.let {
            queryString += if (containsCondition) {
                " AND"
            } else {
                containsCondition = true
                " WHERE"
            }
            queryString += "priceInDollars <= ?"
            bindParameters.add(it)
        }

        if (isCloseToSubway) {
            queryString += if (containsCondition) {
                " AND"
            } else {
                containsCondition = true
                " WHERE"
            }
            queryString += "isCloseToSubway = 1"
        }

        if (isCloseToShops) {
            queryString += if (containsCondition) {
                " AND"
            } else {
                containsCondition = true
                " WHERE"
            }
            queryString += "isCloseToShops = 1"
        }

        if (isCloseToPark) {
            queryString += if (containsCondition) {
                " AND"
            } else {
                containsCondition = true
                " WHERE"
            }
            queryString += "isCloseToPark = 1"
        }

        if (isSold) {
            queryString += if (containsCondition) {
                " AND"
            } else {
                containsCondition = true
                " WHERE"
            }
            queryString += "isSold = 1"
        }

        queryString += ";"
        return Pair(queryString, bindParameters.toArray())
    }

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