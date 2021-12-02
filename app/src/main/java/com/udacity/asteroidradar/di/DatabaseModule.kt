package com.udacity.asteroidradar.di

import com.udacity.asteroidradar.db.AsteroidsDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single { AsteroidsDatabase.getInstance(androidContext()).asteroidsDatabase }
}
