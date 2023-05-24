package com.example.restaurant.data

import androidx.room.*
import com.example.restaurant.domain.models.ResultsItem
import io.reactivex.Single

@Dao
interface PlacesDao {

    @Query("SELECT * FROM table_places")
    fun getAllPlaces(): Single<List<ResultsItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlaces(listPlaces: List<ResultsItem>): Single<List<Long>>

}