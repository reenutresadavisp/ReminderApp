package com.reenu.reminderapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ErrorView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.no_reminder_found_error_msg),
            textAlign = TextAlign.Center,
            color = Color.Red
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderList(viewModel: ReminderViewModel, onAddClick: () -> Unit) {
    val tabIndex = remember { mutableIntStateOf(0) }
    val reminder = remember { viewModel.reminderStateFlow }.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    viewModel.getAllReminder(tabIndex.intValue)
    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.my_reminders_heading)
                        )
                    },
                    scrollBehavior = scrollBehavior
                )
                TabRow(selectedTabIndex = tabIndex.intValue) {
                    Tab(selected = tabIndex.intValue == 0,
                        onClick = { tabIndex.intValue = 0 },
                        text = { Text("Upcoming") },
                        icon = {
                            Image(
                                painter = painterResource(id = R.drawable.check_in),
                                contentDescription = "Upcoming Reminders Tab Icon",
                                modifier = Modifier
                                    .width(24.dp)
                                    .height(24.dp)
                            )
                        })
                    Tab(selected = tabIndex.intValue == 1,
                        onClick = { tabIndex.intValue = 1 },
                        text = { Text("All") },
                        icon = {
                            Image(
                                painter = painterResource(id = R.drawable.all),
                                contentDescription = "All Reminders Tab Icon",
                                modifier = Modifier
                                    .width(24.dp)
                                    .height(24.dp)
                            )
                        })
                    Tab(selected = tabIndex.intValue == 2,
                        onClick = { tabIndex.intValue = 2 },
                        text = { Text("Completed") },
                        icon = {
                            Image(
                                painter = painterResource(id = R.drawable.activity),
                                contentDescription = "Completed Reminders Tab Icon",
                                modifier = Modifier
                                    .width(24.dp)
                                    .height(24.dp)
                            )
                        })
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onAddClick() }, shape = CircleShape) {
                Icon(Icons.Filled.Add, stringResource(R.string.add_new_reminder_content_desc))
            }
        }) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(10.dp)
        ) {
            if (reminder.value.reminders.isNullOrEmpty()) {
                ErrorView()
            }
            LazyColumn() {
                val entries = reminder.value.reminders?.toList() ?: arrayListOf()
                items(entries) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(text = it.title)
                        Text(text = "${it.date} ${it.time} ")
                    }
                }
            }
        }
    }
}