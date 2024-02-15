package com.reenu.reminderapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import java.time.Duration
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderDetail(viewModel: ReminderViewModel, onSuccess: (Long, String?, Duration?) -> Unit) {

    val name = remember { mutableStateOf("") }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val uiState = remember { viewModel.reminderStateFlow }.collectAsState()

    if (uiState.value.scheduleAndNavigateBack) {
        onSuccess(uiState.value.reminderId, uiState.value.message, uiState.value.duration)
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.new_reminder_heading)
                        )
                    },
                    scrollBehavior = scrollBehavior
                )
            }) { it ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(it)
                    .padding(10.dp)
            ) {
                TextField(
                    value = name.value, onValueChange = { value -> name.value = value }, modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.White
                        )
                        .padding(bottom = 16.dp)
                )
                DateAndTimeView(
                    uiState,
                    { date -> viewModel.updateDate(date) },
                    { hour, minute, unit -> viewModel.updateTime(hour, minute, unit) })

            }

            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), onClick = {
                    viewModel.addReminder(name.value)
                }) {
                    Text(stringResource(R.string.add_reminder_btn))
                }
            }
        }
    }
}

@Composable
fun DateAndTimeView(
    uiState: State<ReminderUiState>,
    onDateClick: (LocalDate) -> Unit,
    onTimeClick: (Int, Int, String) -> Unit
) {
    val dateClicked = remember { mutableStateOf(true) }
    val timeClicked = remember { mutableStateOf(false) }
   /* val date = remember { mutableStateOf(uiState.value.dateTime.reminderDate) }
    val time = remember { mutableStateOf(uiState.value.dateTime.reminderTime) }*/
    val date = uiState.value.dateTime.reminderDate
    val time = uiState.value.dateTime.reminderTime
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.DateRange, contentDescription = stringResource(R.string.add_time))
            Text(stringResource(R.string.add_time), modifier = Modifier.padding(start = 10.dp))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = date.formattedDate, modifier = Modifier.clickable {
                dateClicked.value = true
                timeClicked.value = false
            }, textDecoration = TextDecoration.Underline)
            Text(text = time.formattedTime, modifier = Modifier.clickable {
                timeClicked.value = true
                dateClicked.value = false
            }, textDecoration = TextDecoration.Underline)
        }
        if (dateClicked.value) {
            CalendarView(date, onDateClick)
        }
        if (timeClicked.value) {
            TimerView(time, onTimeClick)
        }
        /* Row(
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(bottom = 8.dp), verticalAlignment = Alignment.CenterVertically
         ) {
             Icon(Icons.Filled.Refresh, contentDescription = stringResource(R.string.don_t_repeat))
             Text(stringResource(R.string.don_t_repeat), modifier = Modifier.padding(start = 10.dp))
         }*/
    }

}

