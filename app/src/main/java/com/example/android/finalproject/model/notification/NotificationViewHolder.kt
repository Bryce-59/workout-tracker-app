package com.example.android.finalproject.model.notification

import android.view.View
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.finalproject.R
import com.example.android.finalproject.WordListAdapter
import com.example.android.finalproject.model.notification.alarm.OnToggleAlarmListener
import java.lang.String

class NotificationViewHolder(itemView: View, listener: OnToggleAlarmListener, nodeListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private val deleteImage: ImageView = itemView.findViewById(R.id.delete)
    private val notificationTime: TextView
    private val notificationRecurring: ImageView
    var notificationStarted: Switch
    private val listener: OnToggleAlarmListener
    private val nodeListener: OnItemClickListener
    private var current_notification: Notification?
    private var notificationDates : TextView

    init {
        current_notification = null
        notificationTime = itemView.findViewById(R.id.notification_time)
        notificationStarted = itemView.findViewById(R.id.is_active)
        notificationRecurring = itemView.findViewById(R.id.repeat_weekly)
        notificationDates = itemView.findViewById(R.id.days_active)
        this.listener = listener
        this.nodeListener = nodeListener
    }

    fun bind(notification: Notification) {
        current_notification = notification
        val notificationText = String.format("%02d:%02d", notification.hour, notification.minute)
        notificationTime.text = notificationText
        notificationStarted.isChecked = notification.active
        notificationDates.text = notification.getRecurringDaysText()
        if (notification.weekly) {
            notificationRecurring.setImageResource(R.drawable.ic_repeat_black_24dp)
        }



        notificationStarted.setOnCheckedChangeListener { buttonView, isChecked ->
            listener.onToggle(
                notification
            )
        }
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