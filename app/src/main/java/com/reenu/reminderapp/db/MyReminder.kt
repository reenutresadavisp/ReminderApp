package com.reenu.reminderapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MyReminder(
   @PrimaryKey val title:String,
    val date: String,
    val time: String
)
