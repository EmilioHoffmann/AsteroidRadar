package com.udacity.asteroidradar.db

import androidx.room.TypeConverter
import com.udacity.asteroidradar.utils.dateToString
import com.udacity.asteroidradar.utils.stringToDate
import java.util.Date

class Converters {
    @TypeConverter
    fun dateFromString(value: String?): Date? {
        return value?.let { stringToDate(it) }
    }

    @TypeConverter
    fun stringFromDate(date: Date?): String? {
        return date?.let { dateToString(it) }
    }
}
