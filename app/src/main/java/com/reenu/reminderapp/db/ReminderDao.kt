package com.reenu.reminderapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReminderDao {

    @Query("Select * from MyReminder")
    suspend fun getAllReminder():List<MyReminder>

    @Insert
    suspend fun addReminder(reminder: MyReminder)
}