package com.openclassrooms.realestatemanager.view.realty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.di.Injection
import com.openclassrooms.realestatemanager.model.RealtyActivityState
import com.openclassrooms.realestatemanager.model.RealtyModelStore
import com.openclassrooms.realestatemanager.view.EventObservable
import com.openclassrooms.realestatemanager.view.StateSubscriber
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign

class RealtyActivity : AppCompatActivity(),
        EventObservable<RealtyViewEvent>,
        StateSubscriber<RealtyActivityState> {

    private val realtyModelStore = RealtyModelStore

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        disposables += events().subscribe()
        disposables += realtyModelStore.modelState().subscribeToState()
    }

    override fun onPause() {
        super.onPause()
        disposables.clear()
    }

    override fun events(): Observable<RealtyViewEvent> {
        TODO("here merge ui events")
    }

    override fun Observable<RealtyActivityState>.subscribeToState(): Disposable {
        TODO("here subscribe to the ui state and update ui from state")
    }


}
