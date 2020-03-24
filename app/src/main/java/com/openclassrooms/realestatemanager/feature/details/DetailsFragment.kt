package com.openclassrooms.realestatemanager.feature.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.model.Realty
import com.openclassrooms.realestatemanager.databinding.FragmentDetailsBinding
import com.openclassrooms.realestatemanager.utils.visible
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_details.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentDetailsBinding

    private val detailsViewModel: DetailsViewModel by viewModel()
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        overrideToolbar()
        progressBar.visible = true

        var mapFragment = childFragmentManager.findFragmentById(R.id.staticMap) as SupportMapFragment?
        if (mapFragment == null) {
            val options = GoogleMapOptions().liteMode(true)
            mapFragment = SupportMapFragment.newInstance(options)
            mapFragment.getMapAsync(this)
        }
        childFragmentManager.beginTransaction()
                .replace(R.id.staticMap, mapFragment as Fragment)
                .commit()

        detailsViewModel.currentRealty.observe(viewLifecycleOwner) { realty ->
            realty?.let {
                render(it)
            }
        }
    }

    private fun render(realty: Realty) {
        progressBar.visible = false
        binding.realty = realty
    }

    private fun overrideToolbar() {
        val toolbar = activity?.findViewById<Toolbar>(R.id.toolBar)
        toolbar?.setOnMenuItemClickListener {
            return@setOnMenuItemClickListener if (it.itemId == R.id.editRealty) {
                val action = DetailsFragmentDirections.actionDetailsFragmentToEditRealtyFragment()
                findNavController().navigate(action)
                true
            } else false
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {

    }
}
