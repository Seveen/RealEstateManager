package com.openclassrooms.realestatemanager.feature.mainactivity

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.openclassrooms.realestatemanager.PhoneNavGraphDirections
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.feature.allrealty.AllRealtyFragmentDirections
import com.openclassrooms.realestatemanager.feature.details.DetailsFragmentDirections
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
            R.id.addAgentFragment -> renderAddAgentMenu()
            R.id.loanCalculatorFragment -> renderCalculatorMenu()
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

        //Checks connectivity and launches geolocation updating if network is present
        ConnectionLiveData(applicationContext).observe(this) { isConnected ->
            if (isConnected) {
                Log.d("MAIN", "Updating geolocations")
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
        toolBar.menu.findItem(R.id.loanCalculator).isVisible = true
        toolBar.menu.findItem(R.id.map).isVisible = true
        toolBar.menu.findItem(R.id.newRealty).isVisible = true
        toolBar.menu.findItem(R.id.editRealty).isVisible = false
        backButton.visible = false
    }

    private fun renderDetailsMenu() {
        fab.visible = false
        toolBar.menu.findItem(R.id.loanCalculator).isVisible = false
        toolBar.menu.findItem(R.id.map).isVisible = false
        toolBar.menu.findItem(R.id.newRealty).isVisible = false
        toolBar.menu.findItem(R.id.editRealty).isVisible = true
        backButton.visible = true
    }

    private fun renderEditMenu() {
        fab.visible = false
        toolBar.menu.findItem(R.id.loanCalculator).isVisible = false
        toolBar.menu.findItem(R.id.map).isVisible = false
        toolBar.menu.findItem(R.id.newRealty).isVisible = false
        toolBar.menu.findItem(R.id.editRealty).isVisible = false
        backButton.visible = true
    }

    private fun renderMapMenu() {
        toolBarTitle.text = "Map"
        fab.visible = false
        toolBar.menu.findItem(R.id.loanCalculator).isVisible = false
        toolBar.menu.findItem(R.id.map).isVisible = false
        toolBar.menu.findItem(R.id.newRealty).isVisible = false
        backButton.visible = true
    }

    private fun renderSearchMenu() {
        toolBarTitle.text = "Search realty"
        fab.visible = false
        toolBar.menu.findItem(R.id.loanCalculator).isVisible = false
        toolBar.menu.findItem(R.id.map).isVisible = false
        toolBar.menu.findItem(R.id.newRealty).isVisible = false
        toolBar.menu.findItem(R.id.editRealty).isVisible = false
        backButton.visible = true
    }

    private fun renderAddAgentMenu() {
        toolBarTitle.text = "Add agent"
        fab.visible = false
        toolBar.menu.findItem(R.id.loanCalculator).isVisible = false
        toolBar.menu.findItem(R.id.map).isVisible = false
        toolBar.menu.findItem(R.id.newRealty).isVisible = false
        toolBar.menu.findItem(R.id.editRealty).isVisible = false
        backButton.visible = true
    }

    private fun renderCalculatorMenu() {
        toolBarTitle.text = "Loan calculator"
        fab.visible = false
        toolBar.menu.findItem(R.id.loanCalculator).isVisible = false
        toolBar.menu.findItem(R.id.map).isVisible = false
        toolBar.menu.findItem(R.id.newRealty).isVisible = false
        toolBar.menu.findItem(R.id.editRealty).isVisible = false
        backButton.visible = true
    }

    private fun setupToolbar() {
        toolBarTitle.text = "Real Estate Manager"
        toolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.loanCalculator -> {
                    val action = PhoneNavGraphDirections.actionGlobalLoanCalculatorFragment()
                    navController.navigate(action)
                    true
                }
                R.id.newRealty -> {
                    showPopupAdd()
                    true
                }
                R.id.editRealty -> {
                    val action = DetailsFragmentDirections.actionDetailsFragmentToEditRealtyFragment()
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

    private fun showPopupAdd() {
        val popup = PopupMenu(this, toolBar, Gravity.END)
        popup.inflate(R.menu.add_menu)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.addAgent -> {
                    val action = PhoneNavGraphDirections.actionGlobalAddAgentFragment()
                    navController.navigate(action)
                    true
                }
                R.id.addRealty -> {
                    mainViewModel.clearCurrentRealty()
                    val action = AllRealtyFragmentDirections.actionAllRealtyFragmentToEditRealtyFragment()
                    navController.navigate(action)
                    true
                }
                else -> false

            }
        }
        popup.show()
    }
}
