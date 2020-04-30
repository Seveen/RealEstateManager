package com.openclassrooms.realestatemanager.feature.loancalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.utils.convertToDouble
import com.openclassrooms.realestatemanager.utils.convertToInt
import com.openclassrooms.realestatemanager.utils.validateAndUpdate
import kotlinx.android.synthetic.main.fragment_loan_calculator.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoanCalculatorFragment : Fragment() {

    private val loanCalculatorViewModel: LoanCalculatorViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_loan_calculator, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        wireUi()
    }

    @SuppressLint("SetTextI18n")
    private fun wireUi() {
        amountLayoutView.validateAndUpdate(
                validationFn = { convertToDouble("Not a number") },
                updateFn = { loanCalculatorViewModel.updateAmount(it) }
        )

        contributionLayoutView.validateAndUpdate(
                validationFn = { convertToDouble("Not a number") },
                updateFn = { loanCalculatorViewModel.updateContribution(it) }
        )

        interestRateLayoutView.validateAndUpdate(
                validationFn = { convertToDouble("Not a number") },
                updateFn = { loanCalculatorViewModel.updateInterestRate(it) }
        )

        durationLayoutView.validateAndUpdate(
                validationFn = { convertToInt("Not a number") },
                updateFn = { loanCalculatorViewModel.updateDuration(it) }
        )

        loanCalculatorViewModel.interests.observe(viewLifecycleOwner) {
            interestsTextView.text = "$it $"
        }

        loanCalculatorViewModel.monthlyInstallments.observe(viewLifecycleOwner) {
            monthlyInstallmentsTextView.text = "$it $"
        }

        loanCalculatorViewModel.totalCost.observe(viewLifecycleOwner) {
            totalCostTextView.text = "$it $"
        }
    }
}
