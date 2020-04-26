package com.openclassrooms.realestatemanager.feature.loancalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoanCalculatorViewModel: ViewModel() {

    private val amount: MutableLiveData<Double> = MutableLiveData(0.0)
    private val contribution: MutableLiveData<Double> = MutableLiveData(0.0)
    private val interestRate: MutableLiveData<Double> = MutableLiveData(0.0)
    private val duration: MutableLiveData<Int> = MutableLiveData(1)

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

    private val _outstanding = MediatorLiveData<Double>().apply {
        value = 0.0
        addSource(amount) { updateOutstanding() }
        addSource(contribution) { updateOutstanding() }
    }

    private val _interests = MediatorLiveData<Double>().apply {
        value = 0.0
        addSource(_outstanding) { updateInterests() }
        addSource(interestRate) { updateInterests() }
    }

    val interests: LiveData<Double>
        get() = _interests

    private val _totalCost = MediatorLiveData<Double>().apply {
        value = 0.0
        addSource(_outstanding) { updateTotalCost() }
        addSource(interests) { updateTotalCost() }
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

    private fun updateOutstanding() {
        _outstanding.value = (amount.value?: 0.0) - (contribution.value?: 0.0)
    }

    private fun updateInterests() {
        _interests.value =  (_outstanding.value?:1.0) * ((interestRate.value?:1.0) / 100)
    }

    private fun updateTotalCost() {
        _totalCost.value = (_outstanding.value?:1.0) + (_interests.value?:0.0)
    }

    private fun updateMonthlyInstallments() {
        _monthlyInstallments.value = (_totalCost.value?:0.0) / ((duration.value?:1) * 12)
    }
}