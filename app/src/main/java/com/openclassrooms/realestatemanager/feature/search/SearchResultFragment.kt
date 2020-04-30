package com.openclassrooms.realestatemanager.feature.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.utils.visible
import kotlinx.android.synthetic.main.fragment_search.progressBar
import kotlinx.android.synthetic.main.fragment_search_result.*
import kotlinx.android.synthetic.main.fragment_search_result.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchResultFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModel()

    private val adapter = SearchResultAdapter(emptyList()) {
        searchViewModel.setCurrentRealty(it)
        val action = SearchResultFragmentDirections.actionSearchResultFragmentToDetailsFragment()
        findNavController().navigate(action)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater
                .inflate(R.layout.fragment_search_result, container, false)
                .also { view ->
                    with (view.searchResultRecyclerView) {
                        layoutManager = LinearLayoutManager(this@SearchResultFragment.context)
                        adapter = this@SearchResultFragment.adapter
                    }
                }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        progressBar.visible = true
        searchResultRecyclerView.visible = false
        emptyState.visible = false

        searchViewModel.searchResult.observe(viewLifecycleOwner) { result ->
            render(result)
        }
    }

    private fun render(realtyList: List<Realty>) {
        progressBar.visible = false
        searchResultRecyclerView.visible = realtyList.isEmpty().not()
        emptyState.visible = realtyList.isEmpty()

        adapter.updateData(realtyList)
    }
}