package com.example.restaurant.ui.places

import androidx.lifecycle.ViewModel
import com.example.restaurant.domain.models.ResultsItem
import com.example.restaurant.domain.usecases.GetSavedPlacesUseCase
import com.example.restaurant.domain.models.Result
import io.reactivex.Observable

/**
 */
class NearbyPlacesViewModel(private val getSavedPlacesUseCase: GetSavedPlacesUseCase) : ViewModel() {

    fun getSavedPlaces(): Observable<Result<List<ResultsItem>>> {
        return getSavedPlacesUseCase.execute()
    }

}