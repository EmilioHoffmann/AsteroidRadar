package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.udacity.asteroidradar.api.NasaApiService
import com.udacity.asteroidradar.api.models.Asteroid
import com.udacity.asteroidradar.api.models.PictureOfDay
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.db.AsteroidsDao
import com.udacity.asteroidradar.utils.getToday
import com.udacity.asteroidradar.utils.getWeekFirstDay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val apiService: NasaApiService,
    private val database: AsteroidsDao
) : ViewModel() {

    val allAsteroids: LiveData<List<Asteroid>>
        get() = database.getAllAsteroids()

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    private val _imageOfDayUrl = MutableLiveData<PictureOfDay>()
    val imageOfDayUrl: LiveData<PictureOfDay>
        get() = _imageOfDayUrl

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun loadApiAsteroids() {
        _isLoading.postValue(true)
        viewModelScope.launch {
            when (val result = apiService.getAsteroids()) {
                is NetworkResponse.Success -> {
                    _isLoading.postValue(false)
                    withContext(Dispatchers.IO) {
                        database.insert(parseAsteroidsJsonResult(result.body))
                    }
                }
                else -> {
                    _isLoading.postValue(false)
                }
            }
        }
    }

    fun loadDatabaseItems(selectedFilter: DatabaseFilter = DatabaseFilter.WEEK) {
        viewModelScope.launch(Dispatchers.IO) {
            when (selectedFilter) {
                DatabaseFilter.WEEK -> _asteroids.postValue(database.getWeekAsteroids(getWeekFirstDay().time))
                DatabaseFilter.DAY -> _asteroids.postValue(database.getTodayAsteroids(getToday().time))
                DatabaseFilter.ALL -> _asteroids.postValue(database.getSavedAsteroids())
            }
        }
    }

    fun getImageOfDay() {
        viewModelScope.launch {
            when (val result = apiService.getImageOfDay()) {
                is NetworkResponse.Success -> {
                    if (result.body.mediaType == "image") {
                        _imageOfDayUrl.postValue(result.body)
                    }
                }
                else -> { // Don't need to handle errors as a default image is used
                }
            }
        }
    }
}
