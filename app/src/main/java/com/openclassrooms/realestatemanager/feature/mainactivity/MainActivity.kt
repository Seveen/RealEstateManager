package com.openclassrooms.realestatemanager.feature.mainactivity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.openclassrooms.realestatemanager.PhoneNavGraphDirections
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.feature.allrealty.AllRealtyFragmentDirections
import com.openclassrooms.realestatemanager.utils.ConnectionLiveData
import com.openclassrooms.realestatemanager.utils.visible
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    private val mainViewModel: MainActivityViewModel by viewModel()

    private val navListener = NavController.OnDestinationChangedListener { _, destination, _ ->
        when (destination.id) {
            R.id.allRealtyFragment -> renderAllRealtyMenu()
            R.id.detailsFragment -> renderDetailsMenu()
            R.id.editRealtyFragment -> renderEditMenu()
            R.id.mapFragment -> renderMapMenu()
            R.id.searchFragment -> renderSearchMenu()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(this, R.id.nav_host)

        setupToolbar()
        backButton.setOnClickListener {
            navController.navigateUp()
        }

        ConnectionLiveData(applicationContext).observe(this) { isConnected ->
            if (isConnected) {
                Log.d(javaClass.canonicalName, "Updating locations")
                mainViewModel.updateGeolocations()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(navListener)
    }

    override fun onPause() {
        super.onPause()
        navController.removeOnDestinationChangedListener(navListener)
    }

    private fun renderAllRealtyMenu() {
        setupToolbar()
        fab.visible = true
        fab.setOnClickListener {
            val action = PhoneNavGraphDirections.actionGlobalSearchFragment()
            navController.navigate(action)
        }
        toolBar.menu.findItem(R.id.map).isVisible = true
        toolBar.menu.findItem(R.id.newRealty).isVisible = true
        toolBar.menu.findItem(R.id.editRealty).isVisible = false
        backButton.visible = false
    }

    private fun renderDetailsMenu() {
        fab.visible = false
        toolBar.menu.findItem(R.id.map).isVisible = false
        toolBar.menu.findItem(R.id.newRealty).isVisible = false
        toolBar.menu.findItem(R.id.editRealty).isVisible = true
        backButton.visible = true
    }

    private fun renderEditMenu() {
        fab.visible = false
        toolBar.menu.findItem(R.id.map).isVisible = false
        toolBar.menu.findItem(R.id.newRealty).isVisible = false
        toolBar.menu.findItem(R.id.editRealty).isVisible = false
        backButton.visible = true
    }

    private fun renderMapMenu() {
        fab.visible = false
        toolBar.menu.findItem(R.id.map).isVisible = false
        toolBar.menu.findItem(R.id.newRealty).isVisible = false
        backButton.visible = true
    }

    private fun renderSearchMenu() {
        fab.visible = false
        toolBar.menu.findItem(R.id.map).isVisible = false
        toolBar.menu.findItem(R.id.newRealty).isVisible = false
        toolBar.menu.findItem(R.id.editRealty).isVisible = false
        backButton.visible = true
    }

    private fun setupToolbar() {
        toolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.newRealty -> {
                    mainViewModel.clearCurrentRealty()
                    val action = AllRealtyFragmentDirections.actionAllRealtyFragmentToEditRealtyFragment()
                    navController.navigate(action)
                    true
                }
                R.id.map -> {
                    val action = PhoneNavGraphDirections.actionGlobalMapFragment()
                    navController.navigate(action)
                    true
                }
                else -> false
            }
        }
    }
}
