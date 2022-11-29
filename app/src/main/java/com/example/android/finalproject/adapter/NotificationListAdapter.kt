package com.example.android.finalproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.finalproject.model.notification.Notification
import com.example.android.finalproject.model.notification.NotificationViewHolder

class NotificationListAdapter(private val nodeListener: NotificationViewHolder.OnItemClickListener) :
    RecyclerView.Adapter<NotificationViewHolder>() {
    private var notifications: List<Notification?>

    init {
        notifications = ArrayList<Notification>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_notification, parent, false)
        return NotificationViewHolder(itemView, nodeListener)
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

    fun setAlarms(alarms: List<Notification?>) {
        notifications = alarms
        notifyDataSetChanged()
    }


}