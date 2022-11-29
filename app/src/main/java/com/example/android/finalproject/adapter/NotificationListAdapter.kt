package com.example.android.finalproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.finalproject.model.notification.Notification
import com.example.android.finalproject.model.notification.NotificationViewHolder
import com.example.android.finalproject.model.notification.alarm.OnToggleAlarmListener

class NotificationListAdapter(listener: OnToggleAlarmListener, private val nodeListener: NotificationViewHolder.OnItemClickListener) :
    RecyclerView.Adapter<NotificationViewHolder>() {
    private var notifications: List<Notification?>
    private val listener: OnToggleAlarmListener

    init {
        notifications = ArrayList<Notification>()
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_notification, parent, false)
        return NotificationViewHolder(itemView, listener, nodeListener)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification: Notification? = notifications[position]
        if (notification != null) {
            holder.bind(notification)
        }
    }

    override fun getItemCount(): Int {
        return notifications.size
    }

    fun setNotifications(notifications: List<Notification>) {
        this.notifications = notifications
        notifyDataSetChanged()
    }

    override fun onViewRecycled(holder: NotificationViewHolder) {
        super.onViewRecycled(holder)
        holder.notificationStarted.setOnCheckedChangeListener(null)
    }

    fun setAlarms(alarms: List<Notification?>) {
        notifications = alarms
        notifyDataSetChanged()
    }


}