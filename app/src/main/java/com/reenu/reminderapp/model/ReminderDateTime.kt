package com.reenu.reminderapp.model

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class ReminderDateTime(val dateTime: LocalDateTime = LocalDateTime.now()) {

    val reminderDate: ReminderDate = ReminderDate(dateTime.toLocalDate())
    val reminderTime: ReminderTime = ReminderTime(dateTime.toLocalTime())

    fun getLocalDateTime(hour: Int, minute: Int, unit: String): LocalDateTime {
        val timeInString = "${hour.addLeadingZero()}:${minute.addLeadingZero()} $unit"
        val formatter = DateTimeFormatter.ofPattern("hh:mm a")
        val localTime = LocalTime.parse(timeInString, formatter)
        return reminderDate.localDate.atTime(localTime)

    }

    fun getLocalDateTime(date: LocalDate): LocalDateTime {
        return date.atTime(reminderTime.localTime)
    }

    fun getDuration(): Duration {
        val localDateTime = LocalDateTime.now()
        return Duration.between(localDateTime, dateTime).abs()
    }

    fun Int.addLeadingZero(): String {
        return if (this < 10) {
            "0$this"
        } else {
            this.toString()
        }
    }

}
