package com.reenu.reminderapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.Duration
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderDetail(viewModel: ReminderViewModel, onSuccess: (Long, String?, Duration?) -> Unit) {

    var name = remember { mutableStateOf("") }
    var timeExpanded = remember { mutableStateOf(false) }

    val uiState = remember { viewModel.reminderStateFlow }.collectAsState()

    if (uiState.value.scheduleAndNavigateBack) {
        onSuccess(uiState.value.reminderId, uiState.value.title, uiState.value.duration)
    } else {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(10.dp)
        ) {
            Text(
                text = "New reminder",
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 10.dp)
            )
            Column(modifier = Modifier.background(Color.White)) {

                TextField(
                    value = name.value, onValueChange = { name.value = it }, modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.White
                        )
                        .padding(bottom = 8.dp)
                )

                if (!timeExpanded.value) {

                    Row(modifier = Modifier.clickable { timeExpanded.value = true }) {
                        Icon(Icons.Filled.DateRange, contentDescription = "Add Time")
                        Text("Add Time")
                    }
                } else {
                    Column {
                        DateAndTimeView(
                            uiState,
                            { timeExpanded.value = false },
                            { date -> viewModel.updateDate(date) },
                            { time -> viewModel.updateTime(time) })
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), onClick = {
                viewModel.addReminder(name.value)
            }) {
                Text("Set")
            }
        }
    }
}

@Composable
fun DateAndTimeView(
    uiState: State<ReminderUiState>,
    onClose: () -> Unit,
    onDateClick: (LocalDate) -> Unit,
    onTimeClick: (String) -> Unit
) {
    val dateClicked = remember { mutableStateOf(true) }
    val timeClicked = remember { mutableStateOf(false) }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.DateRange, contentDescription = "Add Time")
            Text("Add time", modifier = Modifier.padding(start = 10.dp))
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                Icon(
                    Icons.Filled.Close, contentDescription = "Close Time",
                    modifier = Modifier.clickable { onClose() }
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = uiState.value.formattedDate ?: "", modifier = Modifier.clickable {
                dateClicked.value = true
                timeClicked.value = false
            })
            Text(text = uiState.value.formattedTime ?: "", modifier = Modifier.clickable {
                timeClicked.value = true
                dateClicked.value = false
            })
        }
        if (dateClicked.value) {
            CalendarView(uiState.value.calendarDate, onDateClick)
        }
        if (timeClicked.value) {
            TimerView(uiState.value.hour, uiState.value.minute, uiState.value.unit, onTimeClick)
        }
    }

}

