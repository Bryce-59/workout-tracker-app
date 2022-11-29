package com.example.android.finalproject.model.notification

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Notification::class], version = 1, exportSchema = false)
abstract class NotificationRoomDatabase : RoomDatabase() {
    abstract fun notiDao(): NotificationDao?

    companion object {
        private var INSTANCE: NotificationRoomDatabase? = null

        fun getDatabase(context: Context): NotificationRoomDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    NotificationRoomDatabase::class.java,
                    "notification_database"
                ).build()
            }
            return INSTANCE
        }
    }
}