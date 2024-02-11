package com.reenu.reminderapp.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MyReminder::class], version = 3)
abstract class ReminderDatabase: RoomDatabase() {
    abstract fun reminderDao(): ReminderDao
}