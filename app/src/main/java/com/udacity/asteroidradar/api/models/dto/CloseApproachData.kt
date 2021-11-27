package com.udacity.asteroidradar.api.models.dto

import com.google.gson.annotations.SerializedName

data class CloseApproachData(
    @SerializedName("miss_distance")
    val missDistance: MissDistance?,
    @SerializedName("relative_velocity")
    val relativeVelocity: RelativeVelocity?
)
