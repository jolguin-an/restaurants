package com.example.restaurant.repositories


import com.example.restaurant.data.PlacesDao
import com.example.restaurant.domain.models.ResultsItem
import com.example.restaurant.domain.repositories.SavePlacesRepository
import io.reactivex.Single

class SavePlacesRepositoryImpl(private val placesDao: PlacesDao) : SavePlacesRepository {
    override fun savePlaces(listPlaces: List<ResultsItem>): Single<List<Long>> {
        return placesDao.insertPlaces(listPlaces)
    }

    override fun getAllPlaces(): Single<List<ResultsItem>> {
        return placesDao.getAllPlaces()
    }


}