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
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.reenu.reminderapp.model.ReminderTime

@Composable
fun TimerView(time: ReminderTime, onTimeClick: (Int, Int, String) -> Unit) {

    val hour = remember { mutableIntStateOf(time.hour) }
    val minute = remember { mutableIntStateOf(time.minute) }
    val unit = remember { mutableStateOf(time.unit) }

    Column {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Incrementer(
                isEnabled = hour.intValue < 12,
                tint = if (hour.intValue < 12) Color.Black else Color.Gray
            ) {
                hour.intValue = (hour.intValue + 1)
            }
            Spacer(
                modifier = Modifier.size(48.dp)
            )
            Incrementer(
                isEnabled = minute.intValue < 59,
                tint = if (minute.intValue < 59) Color.Black else Color.Gray
            ) {
                minute.intValue = (minute.intValue + 1)
            }
            Spacer(
                modifier = Modifier.size(48.dp)
            )
            Incrementer(
                isEnabled = unit.value == "pm",
                tint = if (unit.value == "pm") Color.Black else Color.Gray
            ) {
                unit.value = "am"
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            TimerText(text = hour.intValue.toString())
            TimerText(text = ":")
            TimerText(text = minute.intValue.toString())
            Spacer(
                modifier = Modifier.size(48.dp)
            )
            TimerText(text = unit.value)
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Decrementer(
                isEnabled = hour.intValue > 1,
                tint = if (hour.intValue > 1) Color.Black else Color.Gray
            ) {
                hour.intValue = (hour.intValue) - 1
            }
            Spacer(
                modifier = Modifier.size(48.dp)
            )
            Decrementer(
                isEnabled = minute.intValue > 0,
                tint = if (minute.intValue > 0) Color.Black else Color.Gray
            ) {
                minute.intValue = (minute.intValue) - 1
            }
            Spacer(
                modifier = Modifier.size(48.dp)
            )
            Decrementer(
                isEnabled = unit.value == "am",
                tint = if (unit.value == "am") Color.Black else Color.Gray
            ) {
                unit.value = "pm"
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), onClick = {
            onTimeClick(hour.intValue, minute.intValue, unit.value)
        }) {
            Text("Set")
        }
    }


}

@Composable
fun Incrementer(isEnabled: Boolean, tint: Color, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .size(48.dp)
            .clickable(enabled = isEnabled) { onClick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Icon(
            Icons.Filled.KeyboardArrowUp, "Upwards",
            tint = tint
        )
    }
}

@Composable
fun Decrementer(isEnabled: Boolean, tint: Color, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .size(48.dp)
            .clickable(enabled = isEnabled) { onClick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Icon(
            Icons.Filled.KeyboardArrowDown, "Downwards",
            tint = tint
        )
    }
}

@Composable
fun TimerText(text: String) {
    Column(
        modifier = Modifier.size(48.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text, fontSize = 20.sp)
    }
}

