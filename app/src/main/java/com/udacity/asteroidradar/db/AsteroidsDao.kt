package com.udacity.asteroidradar.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.api.models.Asteroid
import java.util.Date

@Dao
interface AsteroidsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(asteroids: List<Asteroid>)

    @Query("select * from asteroids_table")
    fun getAllAsteroids(): LiveData<List<Asteroid>>

    @Query("DELETE from asteroids_table")
    fun clear()

    @Query("select * from asteroids_table where asteroid_id = :asteroidId LIMIT 1")
    fun getAsteroidById(asteroidId: String): Asteroid

    @Query("DELETE from asteroids_table where close_approach_date < :today")
    fun deleteOldAsteroids(today: Date)
}
