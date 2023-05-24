package com.example.restaurant.repositories

import com.example.restaurant.data.api.ClosePlacesApi
import com.example.restaurant.domain.models.Response
import com.example.restaurant.domain.repositories.ApiPlacesRepository
import com.example.restaurant.domain.repositories.ClosePlacesRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import io.reactivex.Single


class ClosePlacesImpl(api: ClosePlacesApi) : ApiPlacesRepository(api), ClosePlacesRepository {
    override fun getClosePlaces(
        origin: LatLng,
        radius: Int,
        type: String,
        key: String
    ): Single<Response> {
        return api.getClosePlaces(
            "${origin.latitude},${origin.longitude}",
            radius,
            type,
            key
        )
    }
}