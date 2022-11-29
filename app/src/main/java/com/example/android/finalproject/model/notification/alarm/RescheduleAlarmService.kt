package com.learntodroid.simplealarmclock.service

import android.content.Intent
import android.os.IBinder
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.Observer
import com.example.android.finalproject.model.notification.Notification
import com.example.android.finalproject.model.notification.NotificationRepository

class RescheduleAlarmsService : LifecycleService() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val alarmRepository = NotificationRepository(application)
        alarmRepository.getAlarmsLiveData()?.observe(this
        ) { alarms ->
            if (alarms != null) {
                for (a in alarms) {
                    if (a != null) {
                        if (a.active) {
                            a.schedule(applicationContext)
                        }
                    }
                }
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }
}