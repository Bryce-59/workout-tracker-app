/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.finalproject.model.notification

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */

class NotificationRepository(application : Application ) {
    private val notifiDao: NotificationDao?
    private val alarmsLiveData: LiveData<List<Notification?>?>?

    init {
        var db: NotificationRoomDatabase? = NotificationRoomDatabase.getDatabase(application)
        notifiDao = db?.notiDao()
        alarmsLiveData = notifiDao?.getAlarms()
    }

   suspend fun insert(alarm: Notification?) {
        if (alarm != null) {
            notifiDao?.insert(alarm)
        }
    }

    suspend fun update(alarm: Notification?) {
        if (alarm != null) {
            notifiDao?.update(alarm)
        }
    }

    suspend fun delete(id: Int) {
        notifiDao?.deleteNotification(id)
    }

    fun getAlarmsLiveData(): LiveData<List<Notification?>?>? {
        return alarmsLiveData
    }
}
