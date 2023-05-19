package com.example.restaurant.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
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
            .build()
    }
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get(named("api"))))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }
    single {

    }
}
