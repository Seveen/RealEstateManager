package com.openclassrooms.realestatemanager.feature.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.databinding.FragmentDetailsBinding
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.utils.gone
import com.openclassrooms.realestatemanager.utils.visible
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_details.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentDetailsBinding

    private val detailsViewModel: DetailsViewModel by viewModel()
    private val adapter = DetailsPhotoAdapter(emptyList())

    private var map: GoogleMap? = null
    private var currentRealty: Realty? = null

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        progressBar.visible = true

        if (Utils.isInternetAvailable(requireContext())) {
            var mapFragment = childFragmentManager.findFragmentById(R.id.staticMap) as SupportMapFragment?
            if (mapFragment == null) {
                val options = GoogleMapOptions().liteMode(true)
                mapFragment = SupportMapFragment.newInstance(options)
                mapFragment.getMapAsync(this)
            }
            childFragmentManager.beginTransaction()
                    .replace(R.id.staticMap, mapFragment as Fragment)
                    .commit()
        }

        detailsViewModel.currentRealty.observe(viewLifecycleOwner) { realty ->
            realty?.let {
                render(it)
            }
        }
    }

    private fun render(realty: Realty) {
        progressBar.visible = false
        currentRealty = realty
        binding.realty = realty
        adapter.updateData(realty.photos)
        with (realty) {
            closeToLabel.gone = (isCloseToSubway || isCloseToPark || isCloseToShops).not()
            closeToSubwayLabel.gone = isCloseToSubway.not()
            closeToShopsLabel.gone = isCloseToShops.not()
            closeToParkLabel.gone = isCloseToPark.not()
            saleDateLabel.visible = isSold
            saleDateTextView.visible = isSold
        }
        realty.location?.let {
            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 16f))
        }
        detailsViewModel.getAgentById(realty.assignedEstateAgentId).observe(viewLifecycleOwner) {
            binding.agent = it
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap
        currentRealty?.location?.let { location ->
            map?.apply {
                moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16f))
                addMarker(MarkerOptions().position(location))
            }
        }
    }
}
