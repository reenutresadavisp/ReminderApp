package com.reenu.reminderapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import androidx.compose.foundation.lazy.items

@Composable
fun CalendarView(calendarDate: LocalDate?, onDateClick: (LocalDate) -> Unit) {
    var selectedDate by remember { mutableStateOf(calendarDate?:LocalDate.now()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White)

    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { selectedDate = selectedDate.minusMonths(1) }) {
                Text("Prev")
            }
            Text(
                text = "${selectedDate.month} ${selectedDate.year}",
                style = MaterialTheme.typography.headlineSmall
            )
            Button(onClick = { selectedDate = selectedDate.plusMonths(1) }) {
                Text("Next")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        CalendarMonth(selectedDate = selectedDate) { selectedDate = it }
        Spacer(modifier = Modifier.height(8.dp))
        Button(modifier = Modifier.fillMaxWidth().padding(8.dp), onClick = {
            onDateClick(selectedDate) }) {
            Text("Set")
        }
    }
}

@Composable
fun CalendarMonth(selectedDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    val daysInMonth = selectedDate.lengthOfMonth()
    val firstDayOfMonth = selectedDate.withDayOfMonth(1)
    val startDayOfWeek = firstDayOfMonth.dayOfWeek.value // 1 for Monday, 7 for Sunday

    val days = (1..daysInMonth).toList()
    val emptyDays = (1 until startDayOfWeek).map { null }
    val allDays = emptyDays + days

    val daysOfWeek = arrayListOf("Mon","Tue","Wed","Thu","Fri","Sat","Sun")
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        daysOfWeek.forEach{
            Column(modifier = Modifier
                .size(48.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = it)
            }
        }
    }

    LazyColumn {
        items(allDays.chunked(7)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                it.forEach { day ->
                    day?.let { dayOfMonth ->
                        val date = LocalDate.of(selectedDate.year, selectedDate.month, dayOfMonth)
                        DayButton(date = date, isSelected = date == selectedDate) {
                            onDateSelected(date)
                        }
                    } ?: DayButton()
                }
            }
        }
    }
}

@Composable
fun DayButton(date: LocalDate? = null, isSelected: Boolean = false, onDateSelected: (() -> Unit)? = null) {
    Column(
        modifier = Modifier
            .size(48.dp).clickable {  if (onDateSelected != null) {
                onDateSelected()
            } },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = date?.dayOfMonth?.toString() ?: "",
            color = if (isSelected) Color.Blue else Color.Black
        )
    }
}

