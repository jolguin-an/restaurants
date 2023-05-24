package com.example.restaurant.ui

import android.os.Bundle
import com.example.restaurant.R
import com.example.restaurant.databinding.ActivityMainBinding
import com.example.restaurant.ui.base.BaseActivity
import com.example.restaurant.ui.home.HomeFragment
import com.example.restaurant.ui.maps.RestaurantMapFragment
import com.example.restaurant.ui.places.NearbyPlacesFragment


class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate),
    HomeFragment.Listener, NearbyPlacesFragment.Listener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(binding.mToolbar.mainToolbar)

        fragmentContainerId = R.id.fragmentContainerFrameLayout
        navigateTo(HomeFragment.newInstance(), false)

    }

    override fun navigateToMap() {
        navigateTo(RestaurantMapFragment.newInstance())
    }

    override fun navigateToNearbyPlaces() {
        navigateTo(NearbyPlacesFragment.newInstance())
    }
}
