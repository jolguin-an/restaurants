package com.example.restaurant.domain.repositories


import com.example.restaurant.domain.models.ResultsItem
import io.reactivex.Single

interface SavePlacesRepository {
    fun savePlaces(listPlaces: List<ResultsItem>): Single<List<Long>>
    fun getAllPlaces(): Single<List<ResultsItem>>
}