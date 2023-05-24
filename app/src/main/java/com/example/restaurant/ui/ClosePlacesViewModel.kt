package com.example.restaurant.ui

import androidx.lifecycle.ViewModel
import com.example.restaurant.domain.models.Response
import com.example.restaurant.domain.models.Result
import com.example.restaurant.domain.usecases.FindClosePlacesUseCase
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import io.reactivex.Observable


class ClosePlacesViewModel(private val findClosePlacesUseCase: FindClosePlacesUseCase) :
    ViewModel() {
    fun getClosePlaces(
        origin: LatLng,
        radius: Int,
        type: String,
        key: String
    ): Observable<Result<Response>> {
        return findClosePlacesUseCase.execute(origin, radius, type, key)
    }
}