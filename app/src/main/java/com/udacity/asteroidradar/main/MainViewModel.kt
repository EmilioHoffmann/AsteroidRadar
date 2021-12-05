package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.udacity.asteroidradar.api.NasaApiService
import com.udacity.asteroidradar.api.models.Asteroid
import com.udacity.asteroidradar.api.models.PictureOfDay
import com.udacity.asteroidradar.db.AsteroidsDao
import com.udacity.asteroidradar.utils.getToday
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val apiService: NasaApiService,
    private val database: AsteroidsDao
) : ViewModel() {

    val asteroids: LiveData<List<Asteroid>>
        get() = database.getAllAsteroids()

    private val _imageOfDayUrl = MutableLiveData<PictureOfDay>()
    val imageOfDayUrl: LiveData<PictureOfDay>
        get() = _imageOfDayUrl

    fun getAsteroids() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                database.deleteOldAsteroids(getToday().time)
            }
//            when (val result = apiService.getAsteroids()) {
//                is NetworkResponse.Success -> {
//                    withContext(Dispatchers.IO) {
//                        database.insert(parseAsteroidsJsonResult(result.body))
//                    }
//                }
//                else -> { // TODO HANDLE ERROR
//                }
//            }
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
                else -> { // Don't need to handle errors as a default image is used}
                }
            }
        }
    }
}
