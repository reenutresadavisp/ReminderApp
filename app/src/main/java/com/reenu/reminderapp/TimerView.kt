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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimerView(hours: Int, minutes: Int, unit: String?, onTimeClick: (String) -> Unit) {

    val hour  = remember{mutableStateOf(hours)}
    val minute = remember {
        mutableStateOf(minutes)
    }
    val period = remember {
        mutableStateOf(unit?:"am")
    }
    Column() {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .size(48.dp)
                    .clickable(enabled = hour.value<12) {hour.value = (hour.value + 1)},
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,

            ) {
                Icon(Icons.Filled.KeyboardArrowUp, "Upwards",
                    tint = if(hour.value<12)Color.Black else Color.Gray)
            }
            Spacer(
                modifier = Modifier.size(48.dp)
            )
            Column(
                modifier = Modifier.size(48.dp)
                    .clickable(enabled = minute.value<60) {minute.value = (minute.value)+1},
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Filled.KeyboardArrowUp, "Upwards",
                    tint = if(minute.value<60)Color.Black else Color.Gray)
            }
            Spacer(
                modifier = Modifier.size(48.dp)
            )
            Column(
                modifier = Modifier.size(48.dp)
                    .clickable(enabled = period.value=="pm") {period.value = "am"},
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Filled.KeyboardArrowUp, "Upwards",
                    tint = if(period.value=="pm")Color.Black else Color.Gray)
            }

        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier.size(48.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(hour.value.toString(), fontSize = 20.sp)
            }
            Column(
                modifier = Modifier.size(48.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(":", fontSize = 20.sp)
            }
            Column(
                modifier = Modifier.size(48.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(minute.value.toString(), fontSize = 20.sp)
            }
            Spacer(
                modifier = Modifier.size(48.dp)
            )
            Column(
                modifier = Modifier.size(48.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(period.value, fontSize = 20.sp)
            }

        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier.size(48.dp)
                    .clickable(enabled = hour.value>1) {hour.value = (hour.value)-1},
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Filled.KeyboardArrowDown, "Downward",
                        tint = if(hour.value>1)Color.Black else Color.Gray)

            }
            Spacer(
                modifier = Modifier.size(48.dp)
            )
            Column(
                modifier = Modifier.size(48.dp)
                    .clickable(enabled = minute.value>0) {minute.value = (minute.value)-1},
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Filled.KeyboardArrowDown, "Downward",
                    tint = if(minute.value>0)Color.Black else Color.Gray)

            }
            Spacer(
                modifier = Modifier.size(48.dp)
            )
            Column(
                modifier = Modifier.size(48.dp)
                    .clickable(enabled = period.value=="am") {period.value = "pm"},
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Filled.KeyboardArrowDown, "Downward",
                    tint = if(period.value=="am")Color.Black else Color.Gray)

            }

        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(modifier = Modifier.fillMaxWidth().padding(8.dp), onClick = {
            onTimeClick("${hour.value.addLeadingZero()}:${minute.value.addLeadingZero()} ${period.value}")
        }) {
            Text("Set")
        }
    }


}

fun Int.addLeadingZero(): String {
    return if (this < 10) {
        "0$this"
    } else {
        this.toString()
    }
}

