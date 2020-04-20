package com.openclassrooms.realestatemanager.feature.search


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.utils.*
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
                validationFn = { convertToDoubleWithBlankEquals("Not a number", null) },
                updateFn = { searchViewModel.changeLowestPrice(it) }
        )

        highestPriceLayoutView.validateAndUpdate(
                validationFn = { convertToDoubleWithBlankEquals("Not a number", null) },
                updateFn = { searchViewModel.changeHighestPrice(it) }
        )

        minimumSurfaceLayoutView.validateAndUpdate(
                validationFn = { convertToDoubleWithBlankEquals("Not a number", null) },
                updateFn = { searchViewModel.changeMinimumSurface(it) }
        )

        maximumSurfaceLayoutView.validateAndUpdate(
                validationFn = { convertToDoubleWithBlankEquals("Not a number", null) },
                updateFn = { searchViewModel.changeMaximumSurface(it) }
        )

        nbRoomsLayoutView.validateAndUpdate(
                validationFn = { convertToIntWithBlankEquals("Not a number", null) },
                updateFn = { searchViewModel.changeMinimumNbRooms(it) }
        )

        nbBedRoomsLayoutView.validateAndUpdate(
                validationFn = { convertToIntWithBlankEquals("Not a number", null) },
                updateFn = { searchViewModel.changeMinimumNbBedrooms(it) }
        )

        nbBathRoomsLayoutView.validateAndUpdate(
                validationFn = { convertToIntWithBlankEquals("Not a number", null) },
                updateFn = { searchViewModel.changeMinimumNbBathrooms(it) }
        )

        districtSearchLayoutView.validateAndUpdate(
                validationFn = { identity() },
                updateFn = { searchViewModel.changeDistrict(it) }
        )

        houseCheckBox.setOnCheckedChangeListener { _, value -> searchViewModel.changeHouseType(value) }
        flatCheckBox.setOnCheckedChangeListener { _, value -> searchViewModel.changeFlatType(value) }
        duplexCheckBox.setOnCheckedChangeListener { _, value -> searchViewModel.changeDuplexType(value) }
        penthouseCheckBox.setOnCheckedChangeListener { _, value -> searchViewModel.changePenthouseType(value) }

        parkCheckBox.setOnCheckedChangeListener { _, value -> searchViewModel.changeCloseParks(value) }
        subwayCheckBox.setOnCheckedChangeListener { _, value -> searchViewModel.changeCloseSubway(value) }
        shopsCheckBox.setOnCheckedChangeListener { _, value -> searchViewModel.changeCloseShops(value) }

        clearButton.setOnClickListener { clearSearch() }
        searchButton.setOnClickListener { searchRealty() }
    }

    private fun clearSearch() {
        //TODO: Reset ui
        searchViewModel.clear()
    }

    private fun searchRealty() {
        //TODO: no validation errors before navigation
        progressBar.visible = true
        searchButton.visible = false
        searchViewModel.search()
        val action = SearchFragmentDirections.actionSearchFragmentToSearchResultFragment()
        findNavController().navigate(action)
    }
}
