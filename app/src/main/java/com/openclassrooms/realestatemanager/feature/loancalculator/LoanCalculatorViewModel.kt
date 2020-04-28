package com.openclassrooms.realestatemanager.feature.loancalculator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoanCalculatorViewModel: ViewModel() {

    private val calculator = LoanCalculator()

    fun updateAmount(value: Double) {
        calculator.amount = value
        updateResults()
    }

    fun updateContribution(value: Double) {
        calculator.contribution = value
        updateResults()
    }

    fun updateInterestRate(value: Double) {
        calculator.interestRate = value
        updateResults()
    }

    fun updateDuration(value: Int) {
        calculator.duration = value
        updateResults()
    }

    val interests: MutableLiveData<Double> = MutableLiveData()
    val totalCost: MutableLiveData<Double> = MutableLiveData()
    val monthlyInstallments: MutableLiveData<Double> = MutableLiveData()

    private fun updateResults() {
        val (newInterests, newTotalCost, newMonthly) = calculator.notifyDataChanged()
        interests.value = newInterests
        totalCost.value = newTotalCost
        monthlyInstallments.value = newMonthly
    }
}