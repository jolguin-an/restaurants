package com.example.restaurant.ui

import android.app.Application
import com.example.restaurant.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DefaultApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DefaultApplication)
            androidLogger()
            modules(applicationModule)
        }
    }

}