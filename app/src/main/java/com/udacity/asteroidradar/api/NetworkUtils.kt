package com.udacity.asteroidradar.api

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.udacity.asteroidradar.api.models.Asteroid
import com.udacity.asteroidradar.api.models.dto.AsteroidDTO
import com.udacity.asteroidradar.api.models.dto.toAsteroid
import com.udacity.asteroidradar.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

fun parseAsteroidsJsonResult(jsonResult: JsonObject): ArrayList<Asteroid> {
    val nearEarthObjectsJson = jsonResult.getAsJsonObject("near_earth_objects")

    val asteroidList = ArrayList<Asteroid>()

    val nextSevenDaysFormattedDates = getNextSevenDaysFormattedDates()
    for (formattedDate in nextSevenDaysFormattedDates) {
        val dateAsteroidJsonArray = nearEarthObjectsJson.getAsJsonArray(formattedDate)

        for (i in 0 until dateAsteroidJsonArray.size()) {
            val asteroidJson = dateAsteroidJsonArray.get(i)

            val asteroidDto = Gson().fromJson(asteroidJson, AsteroidDTO::class.java)

            val asteroid = asteroidDto.toAsteroid(formattedDate)
            asteroidList.add(asteroid)
        }
    }

    return asteroidList
}

private fun getNextSevenDaysFormattedDates(): ArrayList<String> {
    val formattedDateList = ArrayList<String>()

    val calendar = Calendar.getInstance()
    for (i in 0..Constants.DEFAULT_END_DATE_DAYS) {
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        formattedDateList.add(dateFormat.format(currentTime))
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }

    return formattedDateList
}

fun createNasaApiService(
    authHeaderInterceptor: AuthHeaderInterceptor
): Retrofit {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    val loggingInterceptor =
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }

    val clientBuilder = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authHeaderInterceptor)

    val client = clientBuilder.build()

    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addCallAdapterFactory(NetworkResponseAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
