package com.openclassrooms.realestatemanager.feature.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jakewharton.rxrelay2.PublishRelay
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.app.ViewModelFactory
import com.openclassrooms.realestatemanager.mvibase.MviView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class DetailsFragment : Fragment(), MviView<DetailsIntent, DetailsViewState> {

    private val intentsRelay = PublishRelay.create<DetailsIntent>()
    private val disposables = CompositeDisposable()

    private val viewModel: DetailsViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, ViewModelFactory.getInstance(context!!))
                .get(DetailsViewModel::class.java)
    }

    private val adapter = DetailsPhotoAdapter(emptyList())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
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

    override fun intents(): Observable<DetailsIntent> = intentsRelay

    override fun render(state: DetailsViewState) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
