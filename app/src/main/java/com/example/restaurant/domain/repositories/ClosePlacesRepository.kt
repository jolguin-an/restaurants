package com.example.restaurant.domain.repositories

import com.example.restaurant.domain.models.Response
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import io.reactivex.Single


interface ClosePlacesRepository {
    fun getClosePlaces(
        origin: LatLng,
        radius: Int,
        type: String,
        key: String
    ): Single<Response>
}