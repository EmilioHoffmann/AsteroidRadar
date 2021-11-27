package com.udacity.asteroidradar.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.udacity.asteroidradar.api.NasaApiService
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.launch

class MainViewModel(private val apiService: NasaApiService) : ViewModel() {
    fun getAsteroids() {
        viewModelScope.launch {
            when (val result = apiService.getAsteroids()) {
                is NetworkResponse.Success -> {
                    parseAsteroidsJsonResult(result.body)
                }
                else -> { // TODO HANDLE ERROR
                }
            }
        }
    }
}
