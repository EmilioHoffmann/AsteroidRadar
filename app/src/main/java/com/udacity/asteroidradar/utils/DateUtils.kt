package com.udacity.asteroidradar.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun getToday(): Calendar {
    return Calendar.getInstance()
}

fun getWeekFirstDay(): Calendar {
    val calendar = getToday()
    calendar.add(Calendar.DATE, -7)
    return calendar
}

fun dateToString(date: Date): String? {
    val sdf = SimpleDateFormat(API_DATE_PATTERN, Locale.US)
    return sdf.format(date)
}

fun stringToDate(dateString: String): Date? {
    val sdf = SimpleDateFormat(API_DATE_PATTERN, Locale.US)
    return sdf.parse(dateString)
}

const val API_DATE_PATTERN = "yyyy-MM-dd"
