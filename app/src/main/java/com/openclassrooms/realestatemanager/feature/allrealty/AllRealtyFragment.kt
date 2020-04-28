package com.openclassrooms.realestatemanager.feature.allrealty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.TabletNavGraphDirections
import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.utils.visible
import kotlinx.android.synthetic.main.fragment_all_realty.*
import kotlinx.android.synthetic.main.fragment_all_realty.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class AllRealtyFragment : Fragment() {

    private val allRealtyViewModel: AllRealtyViewModel by viewModel()

    private val adapter = AllRealtyAdapter(emptyList()) {
        allRealtyViewModel.setCurrentRealty(it)
        val action = if (resources.getBoolean(R.bool.isTablet)) {
            TabletNavGraphDirections.actionGlobalDetailsFragment()
        } else {
            AllRealtyFragmentDirections.actionAllRealtyFragmentToDetailsFragment()
        }
        findNavController().navigate(action)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater
                .inflate(R.layout.fragment_all_realty, container, false)
                .also { view ->
                    with (view.allRealtyRecyclerView) {
                        layoutManager = LinearLayoutManager(this@AllRealtyFragment.context)
                        adapter = this@AllRealtyFragment.adapter
                    }
                }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        progressBar.visible = true
        allRealtyRecyclerView.visible = false
        emptyState.visible = false

        allRealtyViewModel.realtyList.observe(viewLifecycleOwner) { result ->
            render(result)
        }
    }

    private fun render(realtyList: List<Realty>) {
        progressBar.visible = false
        allRealtyRecyclerView.visible = realtyList.isEmpty().not()
        emptyState.visible = realtyList.isEmpty()

        adapter.updateData(realtyList)
    }
}
