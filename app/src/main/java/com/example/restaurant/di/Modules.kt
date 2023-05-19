package com.example.restaurant.di

import org.koin.dsl.module


// Repositories
val repositoriesModule = module {

}

// Use cases
val useCasesModule = module {

}

// UI
val viewModelsModule = module {

}

val applicationModule = listOf(
    apiModule,
    repositoriesModule,
    useCasesModule,
    viewModelsModule
)