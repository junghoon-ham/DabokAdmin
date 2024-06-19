package com.hampson.dabokadmin.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object DateUtil {
    private const val DATE_FORMAT = "yyyy-MM-dd"

    fun getTomorrowDate(): String {
        val calendar = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, 1)
        }

        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}