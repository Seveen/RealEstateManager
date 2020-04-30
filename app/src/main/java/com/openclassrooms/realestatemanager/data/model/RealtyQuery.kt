package com.openclassrooms.realestatemanager.data.model

data class RealtyQuery(
    val types: Set<String>,
    val lowestPrice: Double?,
    val highestPrice: Double?,
    val minimumSurface: Double?,
    val maximumSurface: Double?,
    val minimumNbRooms: Int?,
    val minimumNbBathrooms: Int?,
    val minimumNbBedrooms: Int?,
    val district: String?,
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
            queryString += " priceInDollars <= ?"
            bindParameters.add(it)
        }

        minimumSurface?.let {
            queryString += if (containsCondition) {
                " AND"
            } else {
                containsCondition = true
                " WHERE"
            }
            queryString += " surface >= ?"
            bindParameters.add(it)
        }

        maximumSurface?.let {
            queryString += if (containsCondition) {
                " AND"
            } else {
                containsCondition = true
                " WHERE"
            }
            queryString += " surface <= ?"
            bindParameters.add(it)
        }

        minimumNbRooms?.let {
            queryString += if (containsCondition) {
                " AND"
            } else {
                containsCondition = true
                " WHERE"
            }
            queryString += " numberOfRooms >= ?"
            bindParameters.add(it)
        }

        minimumNbBathrooms?.let {
            queryString += if (containsCondition) {
                " AND"
            } else {
                containsCondition = true
                " WHERE"
            }
            queryString += " numberOfBathrooms >= ?"
            bindParameters.add(it)
        }

        minimumNbBedrooms?.let {
            queryString += if (containsCondition) {
                " AND"
            } else {
                containsCondition = true
                " WHERE"
            }
            queryString += " numberOfBedrooms >= ?"
            bindParameters.add(it)
        }

        district?.let {
            queryString += if (containsCondition) {
                " AND"
            } else {
                containsCondition = true
                " WHERE"
            }
            queryString += " district = ?"
            bindParameters.add(it)
        }

        if (types.isEmpty().not()) {
            queryString += if (containsCondition) {
                " AND"
            } else {
                containsCondition = true
                " WHERE"
            }

            queryString += " ("

            var chainingTypes = false
            types.forEach {
                if (chainingTypes) {
                    queryString += " OR"
                } else {
                    chainingTypes = true
                }
                queryString += " type = ?"
                bindParameters.add(it)
            }

            queryString += " )"
        }

        if (isCloseToSubway) {
            queryString += if (containsCondition) {
                " AND"
            } else {
                containsCondition = true
                " WHERE"
            }
            queryString += " isCloseToSubway = 1"
        }

        if (isCloseToShops) {
            queryString += if (containsCondition) {
                " AND"
            } else {
                containsCondition = true
                " WHERE"
            }
            queryString += " isCloseToShops = 1"
        }

        if (isCloseToPark) {
            queryString += if (containsCondition) {
                " AND"
            } else {
                containsCondition = true
                " WHERE"
            }
            queryString += " isCloseToPark = 1"
        }

        if (isSold) {
            queryString += if (containsCondition) {
                " AND"
            } else {
                containsCondition = true
                " WHERE"
            }
            queryString += " isSold = 1"
        }

        queryString += ";"

        return Pair(queryString, bindParameters.toArray())
    }

    companion object {
        fun default() = RealtyQuery(
                types = setOf(),
                district = null,
                lowestPrice = null,
                highestPrice = null,
                minimumSurface = null,
                maximumSurface = null,
                minimumNbRooms = null,
                minimumNbBedrooms = null,
                minimumNbBathrooms = null,
                isCloseToSubway = false,
                isCloseToShops = false,
                isCloseToPark = false,
                isSold = false
        )
    }
}