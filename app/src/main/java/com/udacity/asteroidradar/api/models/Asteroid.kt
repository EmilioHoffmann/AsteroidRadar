package com.udacity.asteroidradar.api.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Asteroid(
    val id: String,
    @SerializedName("name")
    val codename: String?,
    val closeApproachDate: String?,
    @SerializedName("absolute_magnitude_h")
    val absoluteMagnitude: Double?,
    val estimatedDiameter: Double?,
    val relativeVelocity: Double?,
    val distanceFromEarth: Double?,
    val isPotentiallyHazardous: Boolean?
) : Parcelable
