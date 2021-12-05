package com.udacity.asteroidradar.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.udacity.asteroidradar.api.models.Asteroid

@Database(entities = [Asteroid::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AsteroidsDatabase() : RoomDatabase() {
    abstract val asteroidsDatabase: AsteroidsDao

    companion object {
        @Volatile
        private var INSTANCE: AsteroidsDatabase? = null

        fun getInstance(context: Context): AsteroidsDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidsDatabase::class.java,
                        "asteroids_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
