package com.reenu.reminderapp.model

import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.Month

data class ReminderDate(val localDate:LocalDate = LocalDate.now()) {
    val formattedDate: String = formatDate()
    private fun formatDate() =
            "${localDate.dayOfMonth} - ${localDate.month.name} - ${localDate.year}"


}
