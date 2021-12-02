package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.udacity.asteroidradar.api.NasaApiService
import com.udacity.asteroidradar.api.models.Asteroid
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.db.AsteroidsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val apiService: NasaApiService,
    private val database: AsteroidsDao
) : ViewModel() {

    val asteroids: LiveData<List<Asteroid>>
        get() = database.getAllAsteroids()

    fun getAsteroids() {
        viewModelScope.launch {
            when (val result = apiService.getAsteroids()) {
                is NetworkResponse.Success -> {
                    withContext(Dispatchers.IO) {
                        database.insert(parseAsteroidsJsonResult(result.body))
                    }
                }
                else -> { // TODO HANDLE ERROR
                }
            }
        }
    }
}
