package com.example.restaurant.domain.usecases


import com.example.restaurant.domain.models.*
import com.example.restaurant.domain.repositories.SavePlacesRepository
import io.reactivex.Observable


class GetSavedPlacesUseCase(private val repository: SavePlacesRepository) {
    fun execute(): Observable<Result<List<ResultsItem>>> {
        return repository.getAllPlaces()
            .toObservable()
            .map { Result.Success(it) as Result<List<ResultsItem>> }
            .startWith(Result.Loading())
            .onErrorReturn { Result.Failure(it) }
    }
}

