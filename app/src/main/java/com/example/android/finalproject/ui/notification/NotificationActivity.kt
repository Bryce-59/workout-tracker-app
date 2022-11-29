///*
// * Copyright (C) 2017 Google Inc.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
package com.example.android.finalproject.ui.notification
//
//import android.app.Activity
//import android.app.AlarmManager
//import android.app.PendingIntent
//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import android.view.MenuItem
//import android.widget.*
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import androidx.lifecycle.observe
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.android.finalproject.*
//import com.example.android.finalproject.databinding.ActivityNotificationBinding
//import com.example.android.finalproject.model.notification.AlarmReceiver
//import com.example.android.finalproject.model.notification.Notification
//import com.example.android.finalproject.model.notification.NotificationViewHolder
//import com.example.android.finalproject.model.notification.NotificationViewModel
//import com.google.android.material.floatingactionbutton.FloatingActionButton
//import com.learntodroid.simplealarmclock.alarmslist.OnToggleAlarmListener
//
///**
// * Activity for entering a word.
// */
//
//class NotificationActivity : AppCompatActivity(), OnToggleAlarmListener {
//    private lateinit var binding: ActivityNotificationBinding
//    private val newNotiActivityRequestCode = 1
//    private lateinit var notificationViewModel: NotificationViewModel
//    private var adapter = NotificationListAdapter(this)
//
//    public override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
//        notificationViewModel = ViewModelProviders.of(this).get(NotificationViewModel::class.java)
//        notificationViewModel.getAlarmsLiveData()?.observe(this,
//            Observer<List<Any?>?> { alarms ->
//                if (alarms != null) {
//                    adapter.setAlarms(alarms as List<Notification?>)
//                }
//            })
//
//        binding = ActivityNotificationBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview_not)
//        adapter = NotificationListAdapter(this)
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        val fab = findViewById<FloatingActionButton>(R.id.fab)
//        fab.setOnClickListener {
//            val intent = Intent(this@NotificationActivity, NewNotificationActivity::class.java)
//            startActivityForResult(intent, newNotiActivityRequestCode)
//        }
//
//        // Add an observer on the LiveData returned by getAlphabetizedWords.
//        // The onChanged() method fires when the observed data changes and the activity is
//        // in the foreground.
//        notificationViewModel.getAlarmsLiveData()?.observe(this,
//            Observer<List<Any?>?> { alarms ->
//                if (alarms != null) {
//                    adapter.setAlarms(alarms as List<Notification?>)
//                }
//            })
//    }
//
//    companion object {
//        const val EXTRA_REPLY = "com.example.android.notificationListsql.REPLY"
//        const val SEARCH_REPLY = "SEARCH.NOTIFICATION"
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
//        super.onActivityResult(requestCode, resultCode, intentData)
//
//        if (requestCode != newNotiActivityRequestCode || resultCode != Activity.RESULT_OK) {
//            Toast.makeText(
//                applicationContext,
//               "Notification cancelled",
//                Toast.LENGTH_LONG
//            ).show()
//        }
//    }
//
//    fun onItemClick(position: Int, view_code: Int) {
//
//        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview_not)
//        var viewHolder: NotificationViewHolder =
//            recyclerView.findViewHolderForAdapterPosition(position) as NotificationViewHolder;
//
//        if (view_code == 3){
//            val current = viewHolder.getNotification()
//            if (current != null) {
//                current.cancelAlarm(this)
//                notificationViewModel.delete(current)
//            }
//        }
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        finish()
//        return super.onOptionsItemSelected(item)
//    }
//
//    fun onToggle(alarm: Notification?) {
//        if (alarm != null) {
//            if (alarm.active) {
//                alarm.cancelAlarm(this)
//                notificationViewModel.update(alarm)
//            } else {
//                alarm.schedule(this)
//                notificationViewModel.update(alarm)
//            }
//        }
//    }
//}