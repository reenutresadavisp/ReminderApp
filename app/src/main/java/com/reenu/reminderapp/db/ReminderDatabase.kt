package com.reenu.reminderapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(entities = [MyReminder::class], version = 4)
@TypeConverters(Converters::class)
abstract class ReminderDatabase: RoomDatabase() {
    abstract fun reminderDao(): ReminderDao
}