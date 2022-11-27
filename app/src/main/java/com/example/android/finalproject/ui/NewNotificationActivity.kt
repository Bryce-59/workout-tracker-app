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

package com.example.android.finalproject.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.android.finalproject.R

/**
 * Activity for entering a word.
 */

class NewNotificationActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_notification)
        // val workoutName = findViewById<EditText>(R.id.workout_name)

        var notificationInfo : Array<out String>? = intent?.getStringArrayExtra(SEARCH_REPLY)

        val startTime = findViewById<TimePicker>(R.id.startTimePicker)
        val weekly = findViewById<ToggleButton>(R.id.weekly_button)

        if (notificationInfo != null) {
            if (notificationInfo.size == 3) {

                // workoutName.setText(workoutInfo[0], TextView.BufferType.EDITABLE)
                val curStartTime = notificationInfo[1].split(":")
                startTime.hour = Integer.parseInt(curStartTime[0])
                startTime.minute = Integer.parseInt(curStartTime[1])
            }
        }

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()

            var startTimeStr = startTime.hour.toString() + ":"
            if (startTime.minute < 10) {
                startTimeStr += "0"
            }
            startTimeStr += startTime.minute.toString()

            var daysStr : String
            daysStr = ""
            val monday = findViewById<Switch>(R.id.monday)
            if (monday.isChecked) {
                daysStr += "M"
            }
            val tuesday = findViewById<Switch>(R.id.tuesday)
            if (tuesday.isChecked) {
                daysStr += "T"
            }
            val wednesday = findViewById<Switch>(R.id.wednesday)
            if (wednesday.isChecked) {
                daysStr += "W"
            }
            val thursday = findViewById<Switch>(R.id.thursday)
            if (thursday.isChecked) {
                daysStr += "Th"
            }
            val friday = findViewById<Switch>(R.id.friday)
            if (friday.isChecked) {

                daysStr += "F"
            }
            val saturday = findViewById<Switch>(R.id.saturday)
            if (saturday.isChecked) {
                daysStr += "Sa"
            }
            val sunday = findViewById<Switch>(R.id.sunday)
            if (sunday.isChecked) {
                daysStr += "Su"
            }

            var weeklyStr = "Yes"
            if (!weekly.isActivated) {
                weeklyStr = "No"
            }

            var notificationInfo = arrayOf(daysStr, startTimeStr, weeklyStr)
            replyIntent.putExtra(EXTRA_REPLY, notificationInfo)
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }

    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.notificationListsql.REPLY"
        const val SEARCH_REPLY = "SEARCH.NOTIFICATION"
    }

}
