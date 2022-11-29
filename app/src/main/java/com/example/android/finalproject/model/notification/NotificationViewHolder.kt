package com.example.android.finalproject.model.notification

import android.view.View
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.finalproject.R
import java.lang.String

class NotificationViewHolder(itemView: View, nodeListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private val deleteImage: ImageView = itemView.findViewById(R.id.delete)
    private val notificationTime: TextView
    private val notificationRecurring: ImageView
    private val nodeListener: OnItemClickListener
    private var current_notification: Notification?
    private var notificationDates : TextView

    init {
        current_notification = null
        notificationTime = itemView.findViewById(R.id.notification_time)
        notificationRecurring = itemView.findViewById(R.id.repeat_weekly)
        notificationDates = itemView.findViewById(R.id.days_active)
        this.nodeListener = nodeListener
    }

    fun bind(notification: Notification) {
        current_notification = notification
        val notificationText = String.format("%02d:%02d", notification.hour, notification.minute)
        notificationTime.text = notificationText
        notificationDates.text = notification.getRecurringDaysText()
    }


    fun getNotification(): Notification? {
        return current_notification
    }

    override fun onClick(v: View?) {
        var view_code: Int = 0
        if (v != null) {
            if (v.id == deleteImage.id) {
                view_code = 3
            }
        }
        val position: Int = adapterPosition
        if (position != RecyclerView.NO_POSITION) {
            nodeListener.onItemClick(position, view_code)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, view_code: Int)
    }
}