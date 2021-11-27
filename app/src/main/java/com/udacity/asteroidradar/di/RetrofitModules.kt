package com.udacity.asteroidradar.di

import com.udacity.asteroidradar.api.AuthHeaderInterceptor
import com.udacity.asteroidradar.api.NasaApiService
import com.udacity.asteroidradar.api.createNasaApiService
import org.koin.dsl.module

val retrofitModule = module {
    single {
        createNasaApiService(get()).create(NasaApiService::class.java)
    }

    single {
        AuthHeaderInterceptor()
    }
}
