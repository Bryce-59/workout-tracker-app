package com.example.android.finalproject.model.notification.alarm

import com.example.android.finalproject.model.notification.Notification


interface OnToggleAlarmListener {
    fun onToggle(alarm: Notification?)
}