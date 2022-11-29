package com.example.android.finalproject.model.notification

import android.view.View
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.finalproject.R
import java.lang.String

class NotificationViewHolder(itemView: View, listener: OnToggleAlarmListener) :
    RecyclerView.ViewHolder(itemView) {
    private val notificationTime: TextView
    private val notificationRecurring: ImageView
    var notificationStarted: Switch
    private val listener: OnToggleAlarmListener
    private var current_notification : Notification?

    init {
        current_notification = null
        notificationTime = itemView.findViewById(R.id.notification_time)
        notificationStarted = itemView.findViewById(R.id.is_active)
        notificationRecurring = itemView.findViewById(R.id.repeat_weekly)
        this.listener = listener
    }

    fun bind(notification: Notification) {
        current_notification = notification
        val notificationText = String.format("%02d:%02d", notification.hour, notification.minute)
        notificationTime.text = notificationText
        notificationStarted.isChecked = notification.active
        if (notification.weekly) {
            notificationRecurring.setImageResource(R.drawable.ic_repeat_black_24dp)
        } else {
            notificationRecurring.setImageResource(R.drawable.ic_looks_one_black_24dp)
        }

        notificationStarted.setOnCheckedChangeListener { buttonView, isChecked ->
           listener.onToggle(
               notification
           )
       }
    }

    fun getNotification() : Notification? {
        return current_notification
    }
}