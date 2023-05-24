package com.example.restaurant.repositories.base

import com.example.restaurant.domain.repositories.SavePlacesRepository

abstract class ApiRepository(protected val savePlacesRepository: SavePlacesRepository)