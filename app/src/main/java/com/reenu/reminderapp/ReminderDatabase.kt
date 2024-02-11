package com.reenu.reminderapp

import androidx.room.Database
import androidx.room.RoomDatabase
import com.reenu.reminderapp.db.MyReminder
import com.reenu.reminderapp.db.ReminderDao

@Database(entities = [MyReminder::class], version = 2)
abstract class ReminderDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao
}