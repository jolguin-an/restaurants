package com.example.restaurant.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.restaurant.R
import com.example.restaurant.databinding.ActivityMainBinding
import com.example.restaurant.ui.base.BaseActivity
import com.example.restaurant.ui.home.HomeFragment
import com.example.restaurant.ui.maps.RestaurantMapFragment


class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate),
    HomeFragment.Listener {

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
}
