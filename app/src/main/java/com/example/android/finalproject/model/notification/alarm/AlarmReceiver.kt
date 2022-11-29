package com.example.android.finalproject.model.notification.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import com.learntodroid.simplealarmclock.service.RescheduleAlarmsService
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val toastText = String.format("There was a signal")
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            val toastText = String.format("Alarm Reboot")
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            startRescheduleAlarmsService(context)
        } else {
            val toastText = String.format("Alarm Received")
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            if (!intent.getBooleanExtra(RPT, false)) {
                startAlarmService(context, intent)
            }
            run {
                if (alarmIsToday(intent)) {
                    startAlarmService(context, intent)
                }
            }
        }
    }

    private fun alarmIsToday(intent: Intent): Boolean {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        val today = calendar[Calendar.DAY_OF_WEEK]
        when (today) {
            Calendar.SUNDAY -> {
                return intent.getBooleanExtra(SUN, false)
            }
            Calendar.MONDAY -> {
                return intent.getBooleanExtra(MON, false)
            }
            Calendar.TUESDAY -> {
                return intent.getBooleanExtra(TUE, false)
            }
            Calendar.WEDNESDAY -> {
                return intent.getBooleanExtra(WED, false)
            }
            Calendar.THURSDAY -> {
                return intent.getBooleanExtra(THU, false)
            }
            Calendar.FRIDAY -> {
                return intent.getBooleanExtra(FRI, false)
            }
            Calendar.SATURDAY -> {
                return intent.getBooleanExtra(SAT, false)
            }
        }
        return false
    }

    private fun startAlarmService(context: Context, intent: Intent) {
        val intentService = Intent(context, AlarmService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }
    }

    private fun startRescheduleAlarmsService(context: Context) {
        val intentService = Intent(context, RescheduleAlarmsService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }
    }

    companion object {
        const val SUN = "SUNDAY"
        const val MON = "MONDAY"
        const val TUE = "TUESDAY"
        const val WED = "WEDNESDAY"
        const val THU = "THURSDAY"
        const val FRI = "FRIDAY"
        const val SAT = "SATURDAY"
        const val RPT = "WEEKLY"
    }
}