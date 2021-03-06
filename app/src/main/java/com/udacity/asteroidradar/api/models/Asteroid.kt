package com.udacity.asteroidradar.api.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity(tableName = "asteroids_table")
data class Asteroid(
    @PrimaryKey
    @ColumnInfo(name = "asteroid_id")
    val id: String,
    @ColumnInfo(name = "name")
    val codename: String?,
    @ColumnInfo(name = "close_approach_date")
    val closeApproachDate: Date?,
    @ColumnInfo(name = "absolute_magnitude")
    val absoluteMagnitude: Double?,
    @ColumnInfo(name = "estimated_diameter")
    val estimatedDiameter: Double?,
    @ColumnInfo(name = "relative_velocity")
    val relativeVelocity: Double?,
    @ColumnInfo(name = "distance_from_earth")
    val distanceFromEarth: Double?,
    @ColumnInfo(name = "is_potentially_hazardous")
    val isPotentiallyHazardous: Boolean?
) : Parcelable
