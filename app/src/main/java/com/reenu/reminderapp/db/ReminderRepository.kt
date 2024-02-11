package com.reenu.reminderapp.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ReminderRepository @Inject constructor(private val reminderDao: ReminderDao) {
    fun addReminder(name: String, formattedDate: String, formattedTime: String) = flow<String> {
        reminderDao.addReminder(MyReminder(title = name, date = formattedDate, time = formattedTime))
        emit("Success")
    }.flowOn(Dispatchers.IO)

    fun fetchAllReminder() = flow{
        emit(reminderDao.getAllReminder())
    }.flowOn(Dispatchers.IO)
}
