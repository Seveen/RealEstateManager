package com.openclassrooms.realestatemanager.feature.details


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.jakewharton.rxrelay2.PublishRelay
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.app.ViewModelFactory
import com.openclassrooms.realestatemanager.databinding.FragmentDetailsBinding
import com.openclassrooms.realestatemanager.mvibase.MviView
import com.openclassrooms.realestatemanager.utils.visible
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_details.view.*

class DetailsFragment : Fragment(), MviView<DetailsIntent, DetailsViewState> {

    private val intentsRelay = PublishRelay.create<DetailsIntent>()
    private val disposables = CompositeDisposable()
    private lateinit var binding: FragmentDetailsBinding

    private val realtyIdArg: DetailsFragmentArgs by navArgs()

    private val viewModel: DetailsViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, ViewModelFactory.getInstance(context!!))
                .get(DetailsViewModel::class.java)
    }

    private val adapter = DetailsPhotoAdapter(emptyList())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)

        return binding.root
                .also { view ->
                    with(view.photoGalleryRecyclerView) {
                        layoutManager = LinearLayoutManager(this@DetailsFragment.context, HORIZONTAL, false)
                        adapter = this@DetailsFragment.adapter
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

    override fun intents(): Observable<DetailsIntent> = Observable.merge(
            Observable.just(DetailsIntent.LoadRealtyDetailsIntent(realtyIdArg.currentRealtyId)),
            intentsRelay
    )

    override fun render(state: DetailsViewState) {
        Log.d("rtsart", state.toString())
        progressBar.visible = state.isLoading

        state.realty?.let {
            binding.realty = it
        }

        if (state.error != null) {
            Toast.makeText(context, getString(R.string.error_loading_list), Toast.LENGTH_SHORT).show()
            Log.e(this.javaClass.canonicalName, "Error loading realty")
        }
    }


}
