package com.reenu.reminderapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reenu.reminderapp.db.MyReminder
import com.reenu.reminderapp.db.ReminderRepository
import com.reenu.reminderapp.model.ReminderDate
import com.reenu.reminderapp.model.ReminderDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(private val repository: ReminderRepository) :
    ViewModel() {

    val reminderStateFlow = MutableStateFlow(ReminderUiState())


    fun updateDate(date: LocalDate) {
        val oldDateTime = reminderStateFlow.value.dateTime
        reminderStateFlow.update {
            it.copy(
                dateTime = ReminderDateTime(
                    oldDateTime.getLocalDateTime(date)
                )
            )
        }
    }

    fun updateTime(hour: Int, minute: Int, unit: String) {
        val oldDateTime = reminderStateFlow.value.dateTime
        reminderStateFlow.update {
            it.copy(
                dateTime = ReminderDateTime(
                    oldDateTime.getLocalDateTime(hour, minute, unit)
                )
            )
        }

    }

    fun addReminder(title: String) {
        val reminderDateTime: ReminderDateTime = reminderStateFlow.value.dateTime
        viewModelScope.launch {
            repository.addReminder(
                title,
                reminderDateTime.reminderDate.formattedDate,
                reminderDateTime.reminderTime.formattedTime,
                reminderDateTime.dateTime
            ).collect {
                reminderStateFlow.update { uiState ->
                    uiState.copy(
                        reminderId = it,
                        message = title,
                        scheduleAndNavigateBack = true,
                        duration = reminderDateTime.getDuration()
                    )
                }
            }
        }

    }

    fun getAllReminder(tabIndex: Int) {
        viewModelScope.launch {
            when (tabIndex) {
                0 -> {
                    repository.fetchUpcomingReminders()
                        .collect {
                            reminderStateFlow.update { uiState ->
                                uiState.copy(reminders = it)
                            }
                        }
                }

                2 -> {
                    repository.fetchCompletedReminders()
                        .collect {
                            reminderStateFlow.update { uiState ->
                                uiState.copy(reminders = it)
                            }
                        }
                }

                else -> {
                    repository.fetchAllReminders()
                        .collect {
                            reminderStateFlow.update { uiState ->
                                uiState.copy(reminders = it)
                            }
                        }
                }
            }
        }
    }
}

data class ReminderUiState(
    val reminderId: Long = -1L,
    val message: String = "",
    val dateTime: ReminderDateTime = ReminderDateTime(LocalDateTime.now()),
    val reminders: List<MyReminder> = mutableListOf(),
    val scheduleAndNavigateBack: Boolean = false,
    val duration: Duration = Duration.ZERO
)

