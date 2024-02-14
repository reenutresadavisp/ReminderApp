package com.reenu.reminderapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.time.LocalDateTime

@Dao
interface ReminderDao {

    @Query("Select * from MyReminder where status = :status")
    suspend fun getReminders(status: String):List<MyReminder>

    @Insert
    suspend fun addReminder(reminder: MyReminder):Long

    @Query("Select * from MyReminder where id = :id")
    suspend fun getReminder(id:Long):MyReminder

    @Update
    suspend fun updateReminder(reminder:MyReminder)
    @Query("Select * from MyReminder where timeStamp > :now")
    suspend fun getUpcomingReminders(now: LocalDateTime): List<MyReminder>

    @Query("Select * from MyReminder")
    suspend fun getAllReminders(): List<MyReminder>
}