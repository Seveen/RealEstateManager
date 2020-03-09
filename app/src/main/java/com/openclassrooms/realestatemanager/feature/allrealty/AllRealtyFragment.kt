package com.openclassrooms.realestatemanager.feature.allrealty

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxrelay2.PublishRelay
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.app.ViewModelFactory
import com.openclassrooms.realestatemanager.mvibase.MviView
import com.openclassrooms.realestatemanager.utils.visible
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_all_realty.*
import kotlinx.android.synthetic.main.fragment_all_realty.view.*


class AllRealtyFragment : Fragment(), MviView<AllRealtyIntent, AllRealtyViewState> {

    private val intentsRelay = PublishRelay.create<AllRealtyIntent>()
    private val disposables = CompositeDisposable()

    private val viewModel: AllRealtyViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, ViewModelFactory.getInstance(context!!))
                .get(AllRealtyViewModel::class.java)
    }

    private val adapter = AllRealtyAdapter(emptyList()) {
        intentsRelay.accept(AllRealtyIntent.NavigateToDetailsIntent(it))
        val action = AllRealtyFragmentDirections.actionAllRealtyFragmentToDetailsFragment()
        findNavController().navigate(action)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater
                .inflate(R.layout.fragment_all_realty, container, false)
                .also { view ->
                    view.allRealtyRecyclerView.apply {
                        layoutManager = LinearLayoutManager(this@AllRealtyFragment.context)
                        adapter = this@AllRealtyFragment.adapter
                    }
                }
    }

    override fun onStart() {
        super.onStart()
        connect()
    }

    override fun onStop() {
        super.onStop()
        disposables.clear()
    }

    private fun connect() {
        disposables.add(viewModel.states().subscribe(::render))
        viewModel.processIntents(intents())
    }

    override fun intents(): Observable<AllRealtyIntent> = Observable.merge(
            Observable.just(AllRealtyIntent.LoadAllRealtyIntent),
            intentsRelay
    )

     override fun render(state: AllRealtyViewState) {
        progressBar.visible = state.isLoading

        when (state.realty.isEmpty()) {
            true -> {
                allRealtyRecyclerView.visible = false
                emptyState.visible = true
            }
            false -> {
                allRealtyRecyclerView.visible = true
                emptyState.visible = false
                adapter.updateData(state.realty)
            }
        }

        if (state.error != null) {
            Toast.makeText(context, getString(R.string.error_loading_list), Toast.LENGTH_SHORT).show()
            Log.e(this.javaClass.canonicalName, "Error loading realty list")
        }
    }

}
