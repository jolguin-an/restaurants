package com.example.restaurant.di

import androidx.room.Room
import com.example.restaurant.domain.repositories.SavePlacesRepository
import com.example.restaurant.domain.usecases.SavePlacesUseCase
import com.example.restaurant.data.DB_NAME
import com.example.restaurant.data.PlaceDatabase
import com.example.restaurant.domain.repositories.ClosePlacesRepository
import com.example.restaurant.domain.usecases.FindClosePlacesUseCase
import com.example.restaurant.domain.usecases.GetSavedPlacesUseCase
import com.example.restaurant.repositories.ClosePlacesImpl
import com.example.restaurant.repositories.SavePlacesRepositoryImpl
import com.example.restaurant.ui.maps.RestaurantMapViewModel
import com.example.restaurant.ui.places.NearbyPlacesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


// Repositories
val repositoriesModule = module {
    single<SavePlacesRepository> { SavePlacesRepositoryImpl(get()) }
    single<ClosePlacesRepository> { ClosePlacesImpl(get()) }

}

// Use cases
val useCasesModule = module {
    single { SavePlacesUseCase(get()) }
    single { FindClosePlacesUseCase(get()) }
    single { GetSavedPlacesUseCase(get()) }
}

val databaseModule = module {
    single { Room.databaseBuilder(get(), PlaceDatabase::class.java, DB_NAME).build() }
    single { get<PlaceDatabase>().getPlacesDao() }
}

// UI
val viewModelsModule = module {
    viewModel { RestaurantMapViewModel(get(),get()) }
    viewModel { NearbyPlacesViewModel(get()) }
}

val applicationModule = listOf(
    databaseModule,
    apiModule,
    repositoriesModule,
    useCasesModule,
    viewModelsModule
)