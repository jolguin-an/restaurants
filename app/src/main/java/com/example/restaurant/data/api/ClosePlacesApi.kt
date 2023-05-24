package com.example.restaurant.data.api

import com.example.restaurant.domain.models.Response
import com.google.android.libraries.places.api.Places
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface ClosePlacesApi {
    @GET("/maps/api/place/nearbysearch/json")
    fun getClosePlaces(
        @Query("location") origin: String,
        @Query("radius") radius: Int,
        @Query("types") type:String,
        @Query("key") key: String
    ): Single<Response>

}