package com.udacity.asteroidradar.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.api.models.Asteroid
import com.udacity.asteroidradar.db.AsteroidsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(
    private val database: AsteroidsDao
) : ViewModel() {

    private val _asteroidData = MutableLiveData<Asteroid>()
    val asteroidData: LiveData<Asteroid>
        get() = _asteroidData

    fun getAsteroid(asteroidId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _asteroidData.postValue(database.getAsteroidById(asteroidId))
        }
    }
}
