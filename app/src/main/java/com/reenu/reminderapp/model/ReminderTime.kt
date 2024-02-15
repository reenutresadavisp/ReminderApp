package com.reenu.reminderapp.model

import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class ReminderTime(val localTime: LocalTime = LocalTime.now()){
    val formattedTime = convertTo12HourFormat()
    val hour = calculateHour()
    val minute = calculateMinute()
    val unit = calculateUnit()

    private fun calculateUnit(): String {
        val formatter = DateTimeFormatter.ofPattern("HH")
        val hour24Format = localTime.format(formatter).toInt()
        return if (hour24Format < 12) "am" else "pm"
    }

    private fun calculateMinute(): Int {
        val formatter = DateTimeFormatter.ofPattern("mm")
        return localTime.format(formatter).toInt()
    }

    private fun calculateHour(): Int {
        var formatter = DateTimeFormatter.ofPattern("hh")
        return localTime.format(formatter).toInt()
    }

    private fun convertTo12HourFormat(): String {
        val formatter = DateTimeFormatter.ofPattern("hh:mm a")
        return localTime.format(formatter)
    }
}
