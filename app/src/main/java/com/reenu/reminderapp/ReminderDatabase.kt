package com.reenu.reminderapp

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.reenu.reminderapp.db.Converters
import com.reenu.reminderapp.db.MyReminder
import com.reenu.reminderapp.db.ReminderDao

@Database(entities = [MyReminder::class], version = 2)
@TypeConverters(Converters::class)
abstract class ReminderDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao
}