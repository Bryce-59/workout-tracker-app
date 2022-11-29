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

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.android.finalproject.model.notification.alarm.AlarmReceiver
import com.example.android.finalproject.model.notification.alarm.AlarmReceiver.Companion.FRI
import com.example.android.finalproject.model.notification.alarm.AlarmReceiver.Companion.MON
import com.example.android.finalproject.model.notification.alarm.AlarmReceiver.Companion.RPT
import com.example.android.finalproject.model.notification.alarm.AlarmReceiver.Companion.SAT
import com.example.android.finalproject.model.notification.alarm.AlarmReceiver.Companion.SUN
import com.example.android.finalproject.model.notification.alarm.AlarmReceiver.Companion.THU
import com.example.android.finalproject.model.notification.alarm.AlarmReceiver.Companion.TUE
import com.example.android.finalproject.model.notification.alarm.AlarmReceiver.Companion.WED
import java.util.*

@Entity(tableName = "notification_table")
data class Notification(
    @PrimaryKey(autoGenerate = true)
    val notiId: Int,

    val hour: Int,
    val minute: Int,

    val time_created: Long,

    var active: Boolean,

    val sun: Boolean,
    val mon: Boolean,
    val tues: Boolean,
    val wed: Boolean,
    val thurs: Boolean,
    val fri: Boolean,
    val sat: Boolean,

    val weekly: Boolean,
) {

    fun schedule(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(SUN, sun)
        intent.putExtra(MON, mon)
        intent.putExtra(TUE, tues)
        intent.putExtra(WED, wed)
        intent.putExtra(THU, thurs)
        intent.putExtra(FRI, fri)
        intent.putExtra(SAT, sat)
        intent.putExtra(RPT, weekly)
        val alarmPendingIntent = PendingIntent.getBroadcast(context, notiId, intent, FLAG_IMMUTABLE)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar[Calendar.HOUR_OF_DAY] = hour
        calendar[Calendar.MINUTE] = minute
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

        // if alarm time has already passed, increment day by 1
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar[Calendar.DAY_OF_MONTH] = calendar[Calendar.DAY_OF_MONTH] + 1
        }
        if (!weekly) {
            var toastText: String? = null
            try {
                toastText = java.lang.String.format("Workout Notification set")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                alarmPendingIntent
            )
        } else {
            val toastText = String.format("Recurring Workout Notification set")
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
            val RUN_DAILY = (24 * 60 * 60 * 1000).toLong()
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                RUN_DAILY,
                alarmPendingIntent
            )
        }
        this.active = true
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(context, notiId, intent, FLAG_IMMUTABLE)
        alarmManager.cancel(alarmPendingIntent)
        this.active = false
        val toastText =
            String.format("Notification cancelled", hour, minute, notiId)
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
        Log.i("cancel", toastText)
    }

    fun getRecurringDaysText(): String? {
        if (!active) {
            return null
        }

        var days = ""
        if (sun) {
            days += "Su"
        }
        if (mon) {
            days += "M"
        }
        if (tues) {
            days += "Tu"
        }
        if (wed) {
            days += "We"
        }
        if (thurs) {
            days += "Th"
        }
        if (fri) {
            days += "F"
        }
        if (sat) {
            days += "Sa"
        }
        return days
    }
}
