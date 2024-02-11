package com.reenu.reminderapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        Text(text = "No reminder found.", textAlign = TextAlign.Center, color = Color.Red)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderList(viewModel:ReminderViewModel, onAddClick: () -> Unit) {
    val reminder = remember { viewModel.reminderStateFlow }.collectAsState()
    viewModel.getAllReminder()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onAddClick() }, shape = CircleShape) {
                Icon(Icons.Filled.Add, "Add new reminder")
            }
        }) {
        Column(modifier = Modifier.padding(it).padding(10.dp)) {
            Text(
                text = "My reminders",
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 10.dp)
            )
            if (reminder.value.reminders.isNullOrEmpty()) {
                ErrorView()
            }
            LazyColumn() {
                val entries = reminder.value.reminders?.toList()?: arrayListOf()
                items(entries){
                    Column (modifier = Modifier.padding(8.dp)){
                        Text(text = it.title)
                        Text(text = "${it.date} ${it.time} ")
                    }
                }
            }
        }
    }
}