package com.udacity.asteroidradar.api.models.dto

import com.google.gson.annotations.SerializedName

data class RelativeVelocity(
    @SerializedName("kilometers_per_second")
    val kilometersPerSecond: Double?
)
