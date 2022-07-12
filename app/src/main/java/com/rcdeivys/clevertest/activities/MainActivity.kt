package com.rcdeivys.clevertest.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.rcdeivys.clevertest.MobileNavigationDirections
import com.rcdeivys.clevertest.R
import com.rcdeivys.clevertest.common.show
import com.rcdeivys.clevertest.databinding.ActivityMainBinding
import com.rcdeivys.clevertest.models.Result
import com.rcdeivys.clevertest.ui.home.fragments.HomeFragment
import com.rcdeivys.clevertest.ui.locations.LocationsFragment
import com.rcdeivys.clevertest.ui.locations.LocationsFragmentDirections
import com.rcdeivys.clevertest.ui.locationsdetails.LocationsDetailsFragment

class MainActivity : AppCompatActivity(),
    HomeFragment.HomeFragmentListener,
    LocationsFragment.LocationsFragmentListener,
    LocationsDetailsFragment.LocationsDetailsFragmentListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var currentItem: Int = R.id.navigation_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupNightMode()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setupNavigation()
        setContentView(binding.root)
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    if (currentItem != R.id.navigation_home) {
                        currentItem = R.id.navigation_home
                        launchHome()
                        true
                    } else {
                        false
                    }
                }
                R.id.navigation_locations -> {
                    if (currentItem != R.id.navigation_locations) {
                        currentItem = R.id.navigation_locations
                        launchLocations()
                        true
                    } else {
                        false
                    }
                }
                else -> false
            }
        }
    }

    private fun setupNightMode() {
        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    fun showLoading(show: Boolean) {
        binding.layoutProgress.show(show)
    }

    private fun launchHome() {
        val action = MobileNavigationDirections.actionToHomeFragment()
        navController.navigate(action)
    }

    private fun launchLocations() {
        val action = MobileNavigationDirections.actionToLocationsFragment()
        navController.navigate(action)
    }

    override fun launchDetails(character: Result) {
        val action = MobileNavigationDirections.actionToDetailFragment(character)
        navController.navigate(action)
    }

    override fun launchDetailsLocation(location: Result) {
        val action = LocationsFragmentDirections.actionToLocationsDetailsFragment(location)
        navController.navigate(action)
    }
}