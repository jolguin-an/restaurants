package com.example.restaurant.di

import com.example.restaurant.data.api.ClosePlacesApi
import com.example.restaurant.domain.repositories.ApiPlacesRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


// Api
val apiModule = module {
    single<Gson>(named("api")) {
        GsonBuilder()
            .setLenient()
            .create()
    }
    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()
    }
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get(named("api"))))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }
    single {
        provideDefaultApi(get())
    }
}

fun provideDefaultApi(retrofit: Retrofit): ClosePlacesApi =
    retrofit.create(ClosePlacesApi::class.java)