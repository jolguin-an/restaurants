package com.example.restaurant.ui.maps

import androidx.lifecycle.ViewModel
import com.example.restaurant.domain.models.Response
import com.example.restaurant.domain.models.Result
import com.example.restaurant.domain.models.ResultsItem
import com.example.restaurant.domain.usecases.FindClosePlacesUseCase
import com.example.restaurant.domain.usecases.SavePlacesUseCase
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Observable


class RestaurantMapViewModel(
    private val savePlacesUseCase: SavePlacesUseCase,
    private val findClosePlacesUseCase: FindClosePlacesUseCase
) : ViewModel() {

    fun savePlaces(listPlaces: List<ResultsItem>): Observable<Result<ResultsItem>> {
        return savePlacesUseCase.execute(listPlaces)
    }

    fun getClosePlaces(
        origin: LatLng,
        radius: Int,
        type: String,
        key: String
    ): Observable<Result<Response>> {
        return findClosePlacesUseCase.execute(origin, radius, type, key)
    }
}