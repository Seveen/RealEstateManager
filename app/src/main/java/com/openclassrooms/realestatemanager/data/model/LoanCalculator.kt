package com.openclassrooms.realestatemanager.data.model

class LoanCalculator {
    var amount: Double = 0.0
    var contribution: Double = 0.0

    var interestRate: Double = 0.0
    var duration: Int = 1

    private val outstanding
        get() = amount - contribution

    private val interests
        get() = outstanding * (interestRate / 100.0)

    private val totalCost
        get() = outstanding + interests

    private val monthlyInstallments
        get() = totalCost / (duration * 12)

    fun notifyDataChanged() =
        Triple(interests, totalCost, monthlyInstallments)
}