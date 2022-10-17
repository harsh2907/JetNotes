package com.example.notesultimate.domain.util

import java.text.SimpleDateFormat
import java.time.Clock
import java.time.LocalDateTime
import java.util.*

object DateTimeUtil {
    fun getTime(created: Long): String {
        val dateFormat = "dd MMM yyyy"
        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
        val calendar = Calendar.getInstance()

        calendar.timeInMillis = created
        return formatter.format(calendar.time)
    }

}