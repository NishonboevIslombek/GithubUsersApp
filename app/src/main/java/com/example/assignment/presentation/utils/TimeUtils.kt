package com.example.assignment.presentation.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

fun String.getTime(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    try {
        val date = sdf.parse(this)
        val timeInMillis = date?.time ?: 0
        val currentTime = System.currentTimeMillis()
        val timeDifference = currentTime - timeInMillis

        // Convert the time difference to seconds, minutes, hours, days, and years
        val seconds = timeDifference / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val years = days / 365

        return when {
            years > 0 -> "$years year${if (years > 1) "s" else ""} ago"
            days > 0 -> "$days day${if (days > 1) "s" else ""} ago"
            hours > 0 -> "$hours hour${if (hours > 1) "s" else ""} ago"
            minutes > 0 -> "$minutes minute${if (minutes > 1) "s" else ""} ago"
            else -> "$seconds second${if (seconds > 1) "s" else ""} ago"
        }

    } catch (e: ParseException) {
        e.printStackTrace()
        return "Invalid date"
    }
}