package com.udacity.asteroidradar.api

import com.google.gson.JsonObject
import com.haroldadmin.cnradapter.NetworkResponse
import com.udacity.asteroidradar.api.models.PictureOfDay
import okhttp3.ResponseBody
import retrofit2.http.GET

interface NasaApiService {

    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(): NetworkResponse<JsonObject, ResponseBody>

    @GET("/planetary/apod")
    suspend fun getImageOfDay(): NetworkResponse<PictureOfDay, ResponseBody>
}
