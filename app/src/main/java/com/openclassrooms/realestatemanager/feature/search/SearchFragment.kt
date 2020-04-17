package com.openclassrooms.realestatemanager.feature.search


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.utils.convertToDouble
import com.openclassrooms.realestatemanager.utils.validateAndUpdate
import com.openclassrooms.realestatemanager.utils.visible
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        progressBar.visible = false
        searchButton.visible = true

        wireUi()
    }

    private fun wireUi() {
        lowestPriceLayoutView.validateAndUpdate(
                validationFn = { convertToDouble("Not a number") },
                updateFn = { searchViewModel.changeLowestPrice(it) }
        )

        highestPriceLayoutView.validateAndUpdate(
                validationFn = { convertToDouble("Not a number") },
                updateFn = { searchViewModel.changeHighestPrice(it) }
        )

        searchButton.setOnClickListener { searchRealty() }
    }

    private fun searchRealty() {
        //TODO: no validation errors before navigation
        searchViewModel.search()
        val action = SearchFragmentDirections.actionSearchFragmentToSearchResultFragment()
        findNavController().navigate(action)
    }
}
