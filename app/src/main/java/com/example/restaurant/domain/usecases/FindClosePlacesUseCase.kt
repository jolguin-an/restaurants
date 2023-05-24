package com.example.restaurant.domain.usecases

import com.example.restaurant.domain.models.Response
import com.example.restaurant.domain.models.Result
import com.example.restaurant.domain.models.Result.*
import com.example.restaurant.domain.repositories.ClosePlacesRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import io.reactivex.Observable

class FindClosePlacesUseCase(private val repository: ClosePlacesRepository) {
    fun execute(
        origin: LatLng,
        radius: Int,
        type: String,
        key: String
    ): Observable<Result<Response>> {
        return repository.getClosePlaces(
            origin,
            radius,
            type,
            key
        )     .toObservable()
            .map { Success(it) as Result<Response> }
            .startWith(Loading())
            .onErrorReturn { Failure(it) }
    }
}