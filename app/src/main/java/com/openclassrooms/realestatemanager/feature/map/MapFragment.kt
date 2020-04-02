package com.openclassrooms.realestatemanager.feature.map


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.utils.visible
import kotlinx.android.synthetic.main.fragment_map.*
import org.koin.androidx.viewmodel.ext.android.viewModel

//TODO: Own location service to automatically zoom in?
class MapFragment : Fragment(), OnMapReadyCallback {

    private val PERMISSIONS_ACCESS_CODE = 126;

    private lateinit var googleMapFragment: SupportMapFragment
    private var map: GoogleMap? = null

    private val mapViewModel: MapViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        googleMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        googleMapFragment.getMapAsync(this)

        retryButton.setOnClickListener { render() }

        render()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap

        map?.setOnMarkerClickListener {
            mapViewModel.setCurrentRealtyById(it.tag as Int)
            val action = MapFragmentDirections.actionMapFragmentToDetailsFragment()
            findNavController().navigate(action)
            true
        }

        render()

        mapViewModel.realtyList.observe(viewLifecycleOwner) { list ->
            map?.let { map ->
                list.forEach { realty ->
                    realty.location?.let {
                        map.addMarker(MarkerOptions().position(it)).also { marker ->
                            marker.tag = realty.id
                        }
                    }
                }
            }
        }
    }

    private fun render() {
        var shouldShowMap = false

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PermissionChecker.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSIONS_ACCESS_CODE)
        } else {
            map?.isMyLocationEnabled = true
            shouldShowMap = true
        }

        when(shouldShowMap.and(Utils.isInternetAvailable(context))) {
            true -> renderActiveMap()
            false -> renderInactiveMap()
        }
    }

    private fun renderActiveMap() {
        childFragmentManager.beginTransaction().show(googleMapFragment).commit()
        errorState.visible = false
        retryButton.visible = false
    }

    private fun renderInactiveMap() {
        childFragmentManager.beginTransaction().hide(googleMapFragment).commit()
        errorState.visible = true
        retryButton.visible = true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSIONS_ACCESS_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) render()
    }
}
