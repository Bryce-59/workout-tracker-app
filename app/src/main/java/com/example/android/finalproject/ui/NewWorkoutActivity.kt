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
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.android.finalproject.R

/**
 * Activity for entering a word.
 */

class NewWorkoutActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_workout)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val workoutName = findViewById<EditText>(R.id.workout_name)
        val videoLink = findViewById<EditText>(R.id.videoLink)
        var id = ""
        var workoutInfo : Array<out String>? = intent?.getStringArrayExtra(SEARCH_REPLY)
        val startTime = findViewById<TimePicker>(R.id.startTimePicker)
        val endTime = findViewById<TimePicker>(R.id.endTimePicker)

        if (workoutInfo != null) {
            if (workoutInfo.size == 4)
                Log.d("`````", workoutInfo[3])
            id = workoutInfo[3]
            workoutName.setText(workoutInfo[0], TextView.BufferType.EDITABLE)
            videoLink.setText(workoutInfo[4], TextView.BufferType.EDITABLE)
            val curStartTime = workoutInfo[1].split(":")
            startTime.hour = Integer.parseInt(curStartTime[0])
            startTime.minute = Integer.parseInt(curStartTime[1])
            val curEndTime = workoutInfo[2].split(":")
            endTime.hour = Integer.parseInt(curEndTime[0])
            endTime.minute = Integer.parseInt(curEndTime[1])
        }


        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(workoutName.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val workout = workoutName.text.toString()
                var startTimeStr = startTime.hour.toString() + ":"
                if (startTime.minute < 10) {
                    startTimeStr += "0"
                }
                startTimeStr += startTime.minute.toString()
                var endTimeStr = endTime.hour.toString() + ":"
                if (endTime.minute < 10) {
                    endTimeStr += "0"
                }
                endTimeStr += startTime.minute.toString()

                val curLink = videoLink.text.toString()
                var newWorkout = arrayOf(workout, startTimeStr, endTimeStr, id, curLink)
                replyIntent.putExtra(EXTRA_REPLY, newWorkout)
                Log.d("startime", startTimeStr)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }



    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.workoutListsql.REPLY"
        const val SEARCH_REPLY = "SEARCH.WORKOUT"
    }

}
