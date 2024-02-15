package com.reenu.reminderapp

import android.widget.RadioGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepeatReminder() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
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
        }) {
        Column(modifier = Modifier.padding(it)) {
            ReminderRadioGroup()
        }

    }
}

    @Composable
    fun ReminderRadioGroup() {
        val options = listOf("Option 1", "Option 2", "Option 3")
        val selectedOption = remember { mutableStateOf(options[0]) }

        Column {
            options.forEach { option ->
                Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                    RadioButton(
                        selected = selectedOption.value == option,
                        onClick = { selectedOption.value = option },
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(option)
                }
            }
        }
    }
