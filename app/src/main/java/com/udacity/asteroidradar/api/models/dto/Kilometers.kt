package com.udacity.asteroidradar.api.models.dto

import com.google.gson.annotations.SerializedName

data class Kilometers(
    @SerializedName("estimated_diameter_max")
    val estimatedDiameterMax: Double?
)
