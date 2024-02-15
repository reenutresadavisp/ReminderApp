package com.reenu.reminderapp.util

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object DateTimeUtil {

    fun getLocalTime(hour: Int, minute: Int, unit: String): LocalTime{
        val timeInString = "${hour.addLeadingZero()}:${minute.addLeadingZero()} $unit"
        val formatter = DateTimeFormatter.ofPattern("hh:mm a")
        return LocalTime.parse(timeInString, formatter)
    }

    fun getLocalDateTime(time:LocalTime, date: LocalDate): LocalDateTime{
        return date.atTime(time)
    }

    fun getDuration(dateTime:LocalDateTime): Duration {
        val localDateTime = LocalDateTime.now()
        return Duration.between(localDateTime, dateTime).abs()
    }

    private fun Int.addLeadingZero(): String {
        return if (this < 10) {
            "0$this"
        } else {
            this.toString()
        }
    }
}