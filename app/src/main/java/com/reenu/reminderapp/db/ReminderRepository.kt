package com.reenu.reminderapp.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDateTime
import javax.inject.Inject

class ReminderRepository @Inject constructor(private val reminderDao: ReminderDao) {
    fun addReminder(name: String, formattedDate: String, formattedTime: String, now: LocalDateTime?) = flow<Long> {
        val insertedRowId = reminderDao.addReminder(MyReminder(title = name, date = formattedDate, time = formattedTime, status = Status.UPCOMING.name, timeStamp = now))
        emit(insertedRowId)
    }.flowOn(Dispatchers.IO)

    fun fetchUpcomingReminders() = flow{
        emit(reminderDao.getUpcomingReminders(LocalDateTime.now()))
    }.flowOn(Dispatchers.IO)

    fun fetchCompletedReminders() = flow{
        emit(reminderDao.getReminders(Status.COMPLETED.name))
    }.flowOn(Dispatchers.IO)

    fun fetchAllReminders() = flow{
        emit(reminderDao.getAllReminders())
    }.flowOn(Dispatchers.IO)

    suspend fun updateReminder(reminderId:Long) {
        val reminder = reminderDao.getReminder(reminderId).copy(status = Status.COMPLETED.name)
        reminderDao.updateReminder(reminder)
    }
}
