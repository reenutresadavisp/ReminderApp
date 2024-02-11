package com.reenu.reminderapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reenu.reminderapp.db.MyReminder
import com.reenu.reminderapp.db.ReminderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(private val repository: ReminderRepository) : ViewModel() {

    val reminderStateFlow = MutableStateFlow(ReminderUiState())

    init {
        val currentTime = LocalDateTime.now()
        formatDateTime(currentTime.toLocalDate(), currentTime.toLocalTime())

    }

    fun formatDateTime(localDate: LocalDate? = null, localTime: LocalTime? = null) {
        localDate?.let {
            val formattedDate =
                "${it.dayOfMonth} - ${it.month.name} - ${it.year}"
            reminderStateFlow.update { uiState ->
                uiState.copy(formattedDate = formattedDate, calendarDate = localDate)
            }
        }
        localTime?.let {
            val formattedTime = convertTo12HourFormat(localTime)

            var formatter = DateTimeFormatter.ofPattern("hh")
            val hour = localTime.format(formatter).toInt()

            formatter = DateTimeFormatter.ofPattern("mm")
            val minute = localTime.format(formatter).toInt()

            val unit = if(hour<12) "am" else "pm"

            reminderStateFlow.update { uiState ->
                uiState.copy(
                    formattedTime = formattedTime,
                    localTime = it,
                    hour = hour,
                    minute = minute,
                    unit = unit
                )
            }
        }


    }


    fun convertTo12HourFormat(localTime: LocalTime): String {
        val formatter = DateTimeFormatter.ofPattern("hh:mm a")
        return localTime.format(formatter)
    }




    fun updateDate(date: LocalDate) {
        formatDateTime(date)
    }

    fun updateTime(time: String) {
        val formatter = DateTimeFormatter.ofPattern("hh:mm a")
        val localTime = LocalTime.parse(time, formatter)
        formatDateTime(localTime = localTime)

    }

    fun addReminder(title: String) {
        viewModelScope.launch {
            repository.addReminder(
                title,
                reminderStateFlow.value.formattedDate?:"",
                reminderStateFlow.value.formattedTime?:""
            ).collect{
                val currentTime = LocalTime.now()
                val scheduleTime = reminderStateFlow.value.localTime
                reminderStateFlow.update { uiState ->
                    uiState.copy(
                        title = title,
                        scheduleAndNavigateBack = true,
                        duration = Duration.between(currentTime,scheduleTime).abs().toMillis())
                }
            }
        }

    }

    fun getAllReminder(){
        viewModelScope.launch {
            repository.fetchAllReminder()
                .collect{
                    reminderStateFlow.update { uiState ->
                        uiState.copy(reminders = it)
                    }
                }
        }
    }

}

data class ReminderUiState(
    val title:String? = null,
    val formattedDate: String? = null,
    val formattedTime: String? = null,
    val calendarDate: LocalDate? = null,
    val localTime:LocalTime? = null,
    val hour: Int = 0,
    val minute: Int = 0,
    val unit: String? = null,
    val reminders:List<MyReminder>? = null,
    val scheduleAndNavigateBack:Boolean = false,
    val duration:Long = 0L
)