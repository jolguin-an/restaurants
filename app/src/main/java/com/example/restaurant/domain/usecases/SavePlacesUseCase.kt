package com.example.restaurant.domain.usecases


import com.example.restaurant.domain.models.*
import com.example.restaurant.domain.repositories.SavePlacesRepository
import io.reactivex.Observable


class SavePlacesUseCase(private val repository: SavePlacesRepository) {
    fun execute(listPlaces: List<ResultsItem>): Observable<Result<ResultsItem>> {
        return repository.savePlaces(listPlaces)
            .toObservable()
            .map { Result.Success(it) as Result<ResultsItem> }
            .startWith(Result.Loading())
            .onErrorReturn { Result.Failure(it) }
    }
}

