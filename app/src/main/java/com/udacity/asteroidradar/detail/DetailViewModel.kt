package com.udacity.asteroidradar.detail

import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.api.models.Asteroid
import com.udacity.asteroidradar.db.AsteroidsDao

class DetailViewModel(
    private val database: AsteroidsDao
) : ViewModel() {

    fun getAsteroid(asteroidId: String): Asteroid {
        return database.getAsteroidById(asteroidId)
    }
}
