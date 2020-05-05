package com.openclassrooms.realestatemanager

import com.openclassrooms.realestatemanager.data.model.LoanCalculator
import junit.framework.Assert.assertEquals
import org.junit.Test

class CalculatorTest {

    @Test
    fun calculator_isCorrect() {
        val calculator = LoanCalculator()
        val (defaultInterests, defaultTotalCost, defaultMonthly) = calculator.notifyDataChanged()

        assertEquals(0.0, defaultInterests)
        assertEquals(0.0, defaultTotalCost)
        assertEquals(0.0, defaultMonthly)

        calculator.amount = 10000.0
        calculator.contribution = 1000.0
        calculator.interestRate = 1.0
        calculator.duration = 20

        val (interests, totalCost, monthly) = calculator.notifyDataChanged()

        assertEquals(90.0, interests)
        assertEquals(9090.0, totalCost)
        assertEquals(37.875, monthly)
    }
}