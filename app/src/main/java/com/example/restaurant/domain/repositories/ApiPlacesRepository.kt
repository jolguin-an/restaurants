package com.example.restaurant.domain.repositories

import com.example.restaurant.data.api.ClosePlacesApi

abstract class ApiPlacesRepository(protected val api: ClosePlacesApi)