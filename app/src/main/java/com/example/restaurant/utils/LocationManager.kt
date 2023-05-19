package com.example.restaurant.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.google.android.gms.location.*

class GeoLocationManager(private val context: Context) {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var locationRequest=  LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, UPDATE_INTERVAL_MILLISECONDS)
        .setWaitForAccurateLocation(false)
        .setMinUpdateIntervalMillis(FASTEST_UPDATE_INTERVAL_MILLISECONDS)
        .setMaxUpdateDelayMillis(FASTEST_MAX_WAIT_TIME)
        .build()
    private var startedLocationTracking = false

    init {
        configureLocationRequest()
        setupLocationProviderClient()
    }

    private fun setupLocationProviderClient() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    }

    private fun configureLocationRequest() {

       // locationRequest.interval = UPDATE_INTERVAL_MILLISECONDS
        //locationRequest.fastestInterval = FASTEST_UPDATE_INTERVAL_MILLISECONDS
        //locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }



    @SuppressLint("MissingPermission")
    fun startLocationTracking(locationCallback: LocationCallback) {
        if (!startedLocationTracking) {

            fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper())

            this.locationCallback = locationCallback

            startedLocationTracking = true
        }
    }

    fun stopLocationTracking() {
        if (startedLocationTracking) {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

    companion object {
        const val UPDATE_INTERVAL_MILLISECONDS: Long = 0
        const val FASTEST_UPDATE_INTERVAL_MILLISECONDS = UPDATE_INTERVAL_MILLISECONDS / 2
        const val FASTEST_MAX_WAIT_TIME = UPDATE_INTERVAL_MILLISECONDS / 2
    }
}
