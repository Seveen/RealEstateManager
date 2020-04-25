package com.openclassrooms.realestatemanager.feature.loancalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoanCalculatorViewModel: ViewModel() {

    private val amount: MutableLiveData<Double> = MutableLiveData(0.0)
    private val contribution: MutableLiveData<Double> = MutableLiveData(0.0)
    private val interestRate: MutableLiveData<Double> = MutableLiveData(0.0)
    private val duration: MutableLiveData<Int> = MutableLiveData(0)

    fun updateAmount(value: Double) {
        amount.value = value
    }

    fun updateContribution(value: Double) {
        contribution.value = value
    }

    fun updateInterestRate(value: Double) {
        interestRate.value = value
    }

    fun updateDuration(value: Int) {
        duration.value = value
    }

    private val _totalCost = MediatorLiveData<Double>().apply {
        value = 0.0
        addSource(amount) { updateTotalCost() }
        addSource(contribution) { updateTotalCost() }
        addSource(interestRate) { updateTotalCost() }
    }

    val totalCost: LiveData<Double>
        get() = _totalCost

    private val _monthlyInstallments = MediatorLiveData<Double>().apply {
        value = 0.0
        addSource(_totalCost) { updateMonthlyInstallments() }
        addSource(duration) { updateMonthlyInstallments() }
    }

    val monthlyInstallments: LiveData<Double>
        get() = _monthlyInstallments

    private fun updateTotalCost() {
        val outstanding = (amount.value?: 0.0) - (contribution.value?: 0.0)

        _totalCost.value = outstanding + outstanding * (interestRate.value?:1.0)
    }

    private fun updateMonthlyInstallments() {
        _monthlyInstallments.value = (_totalCost.value?:0.0) / (duration.value?:1)
    }


}