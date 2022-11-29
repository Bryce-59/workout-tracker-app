package com.example.android.finalproject.model.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import com.learntodroid.simplealarmclock.service.RescheduleAlarmsService
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
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
                return if (intent.getBooleanExtra(SUN, false)) true else false
            }
            Calendar.MONDAY -> {
                return if (intent.getBooleanExtra(MON, false)) true else false
            }
            Calendar.TUESDAY -> {
                return if (intent.getBooleanExtra(TUE, false)) true else false
            }
            Calendar.WEDNESDAY -> {
                return if (intent.getBooleanExtra(WED, false)) true else false
            }
            Calendar.THURSDAY -> {
                return if (intent.getBooleanExtra(THU, false)) true else false
            }
            Calendar.FRIDAY -> {
                return if (intent.getBooleanExtra(FRI, false)) true else false
            }
            Calendar.SATURDAY -> {
                return if (intent.getBooleanExtra(SAT, false)) true else false
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