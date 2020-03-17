package com.openclassrooms.realestatemanager.feature.editrealty


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import com.jakewharton.rxrelay2.PublishRelay
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.feature.editrealty.EditRealtyIntent.*
import com.openclassrooms.realestatemanager.mvibase.MviView
import com.openclassrooms.realestatemanager.utils.toEditable
import com.openclassrooms.realestatemanager.utils.visible
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_edit_realty.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditRealtyFragment : Fragment(), MviView<EditRealtyIntent, EditRealtyViewState> {

    private val intentsRelay = PublishRelay.create<EditRealtyIntent>()
    private val disposables = CompositeDisposable()

    private val realtyIdArg: EditRealtyFragmentArgs by navArgs()
    private var editedRealty: Realty? = null

    private val editRealtyViewModel: EditRealtyViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_realty, container, false)
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
        disposables.add(editRealtyViewModel.states().subscribe(::render))
        editRealtyViewModel.processIntents(intents())
    }

    override fun intents(): Observable<EditRealtyIntent> = Observable.merge(
            Observable.just(LoadRealtyIntent(realtyIdArg.editRealtyId)),
            districtIntents(),
            descriptionIntents(),
            surfaceIntents())
            .mergeWith(numberRoomsIntents())
            .mergeWith(numberBathroomsIntents())
            .mergeWith(addressIntents())
            .mergeWith(saveIntents())
            .mergeWith(intentsRelay)

    override fun render(state: EditRealtyViewState) {
        /* When loading a realty (that is not the default/new one) we should synchronize UI to reflect
        the realty we're editing */
        if (editedRealty?.id == "default" && state.realty.id != "default") {
            synchronizeUi(state.realty)
        }

        editedRealty = state.realty

        if (state.isProcessing) {
            progressBar.visible = true
            saveButton.visible = false
        } else {
            progressBar.visible = false
            saveButton.visible = true
        }
        //TODO: gérer l'erreur et la navigation
    }

    private fun districtIntents() =
            districtEditText.textChanges()
                    .map { text -> DistrictIntent(text.toString()) }

    private fun descriptionIntents() =
            descriptionEditText.textChanges()
                    .map { text -> DescriptionIntent(text.toString()) }

    private fun surfaceIntents() =
            surfaceEditText.textChanges()
                    .map { text -> SurfaceIntent(text.toString()) }

    private fun numberRoomsIntents() =
            numberRoomsEditText.textChanges()
                    .map { text -> NumberOfRoomsIntent(text.toString()) }

    private fun numberBathroomsIntents() =
            numberBathroomsEditText.textChanges()
                    .map { text -> NumberOfBathroomsIntent(text.toString()) }

    private fun addressIntents() =
            addressEditText.textChanges()
                    .map { text -> AddressIntent(text.toString()) }

    private fun saveIntents() =
            saveButton.clicks()
                    .map { SaveIntent(editedRealty!!) }

    private fun synchronizeUi(realty: Realty) {
        districtEditText.text = realty.district.toEditable()
        descriptionEditText.text = realty.description.toEditable()
        surfaceEditText.text = realty.surface.toString().toEditable()
        numberRoomsEditText.text = realty.numberOfRooms.toString().toEditable()
        numberBathroomsEditText.text = realty.numberOfBathrooms.toString().toEditable()
        addressEditText.text = realty.address.toEditable()
    }
}
