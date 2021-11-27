package com.udacity.asteroidradar.api.models.dto

import com.google.gson.annotations.SerializedName
import com.udacity.asteroidradar.api.models.Asteroid

data class AsteroidDTO(
    val id: String,
    val name: String?,
    @SerializedName("close_approach_data")
    val closeApproachData: List<CloseApproachData>?,
    @SerializedName("absolute_magnitude_h")
    val absoluteMagnitudeH: Double?,
    @SerializedName("estimated_diameter")
    val estimatedDiameter: EstimatedDiameter?,
    @SerializedName("is_potentially_hazardous_asteroid")
    val isPotentiallyHazardousAsteroid: Boolean?
)

fun AsteroidDTO.toAsteroid(formattedDate: String): Asteroid {
    return Asteroid(
        id = id,
        codename = name,
        closeApproachDate = formattedDate,
        absoluteMagnitude = absoluteMagnitudeH,
        estimatedDiameter = estimatedDiameter?.kilometers?.estimatedDiameterMax,
        relativeVelocity = closeApproachData?.firstOrNull()?.relativeVelocity?.kilometersPerSecond,
        distanceFromEarth = closeApproachData?.firstOrNull()?.missDistance?.astronomical,
        isPotentiallyHazardous = isPotentiallyHazardousAsteroid
    )
}
