package com.udacity.asteroidradar

import android.app.Application
import com.udacity.asteroidradar.di.databaseModule
import com.udacity.asteroidradar.di.retrofitModule
import com.udacity.asteroidradar.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

@KoinApiExtension
class AsteroidRadarApplication : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        startKoinDI()
    }

    private fun startKoinDI() {
        startKoin {
            androidContext(this@AsteroidRadarApplication)
            modules(
                listOf(
                    retrofitModule,
                    viewModelModule,
                    databaseModule
                )
            )
        }
    }
}
