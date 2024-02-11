package com.reenu.reminderapp

import android.content.Context
import androidx.room.Room
import com.reenu.reminderapp.db.ReminderDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ReminderHiltModule {

    @Provides
    fun provideRoomClient(@ApplicationContext context: Context): ReminderDatabase {
       return Room.databaseBuilder(
            context,
            ReminderDatabase::class.java, "reminder_db"
        ).build()
    }

    @Provides
    fun provideReminderDao(appDatabase: ReminderDatabase): ReminderDao {
        return appDatabase.reminderDao()
    }


}