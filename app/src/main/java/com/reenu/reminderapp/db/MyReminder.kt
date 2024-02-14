package com.reenu.reminderapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

enum class Status{
    UPCOMING,COMPLETED
}
@Entity
data class MyReminder(
   @PrimaryKey(autoGenerate = true) val id:Long = 0,
   val title:String,
    val date: String,
    val time: String,
    val status:String,
    val timeStamp: LocalDateTime?
)
