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

package com.example.android.finalproject.ui.notification

/**
 * Activity for entering a word.
 */

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import butterknife.BindView
import com.example.android.finalproject.R
import com.example.android.finalproject.model.notification.Notification
import com.example.android.finalproject.model.notification.NotificationViewModel
import java.util.*

class NewNotificationActivity : AppCompatActivity() {

    private var createAlarmViewModel: NotificationViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_notification)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        createAlarmViewModel = ViewModelProviders.of(this).get(
            NotificationViewModel::class.java
        )

        val scheduleAlarm: Button? = findViewById(R.id.saveButton)

        scheduleAlarm!!.setOnClickListener { v ->

            scheduleAlarm()
            finish()
        }
    }

    private fun scheduleAlarm() {
        val timePicker: TimePicker? = findViewById(R.id.pickTime)
        val recurring: CheckBox? = findViewById<CheckBox>(R.id.repeated)
        val sun: CheckBox? = findViewById<CheckBox>(R.id.sunday)
        val mon: CheckBox? = findViewById<CheckBox>(R.id.monday)
        val tue: CheckBox? = findViewById<CheckBox>(R.id.tuesday)
        val wed: CheckBox? = findViewById<CheckBox>(R.id.wednesday)
        val thu: CheckBox? = findViewById<CheckBox>(R.id.thursday)
        val fri: CheckBox? = findViewById<CheckBox>(R.id.friday)
        val sat: CheckBox? = findViewById<CheckBox>(R.id.saturday)

        val notiId = Random().nextInt(Int.MAX_VALUE)
        val notification = Notification(
            notiId,
            timePicker!!.hour,
            timePicker!!.minute,
            System.currentTimeMillis(),
            true,
            sun!!.isChecked,
            mon!!.isChecked,
            tue!!.isChecked,
            wed!!.isChecked,
            thu!!.isChecked,
            fri!!.isChecked,
            sat!!.isChecked,
            recurring!!.isChecked,
        )
        createAlarmViewModel?.insert(notification)
        notification.schedule(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)

    }
}
