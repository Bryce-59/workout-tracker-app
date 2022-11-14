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

package com.example.android.finalproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/**
 * Activity for entering a word.
 */

class NewWorkoutActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_workout)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val workoutName = findViewById<EditText>(R.id.workout_name)

        var id = ""
        var workoutInfo : Array<out String>? = intent?.getStringArrayExtra(SEARCH_REPLY)
        val startTime = findViewById<TimePicker>(R.id.startTimePicker)
        val endTime = findViewById<TimePicker>(R.id.endTimePicker)

        if (workoutInfo != null) {
            if (workoutInfo.size == 4)
                id = workoutInfo[3]
                workoutName.setText(workoutInfo[0], TextView.BufferType.EDITABLE)
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
                val startTimeStr = startTime.hour.toString() + ":" + startTime.minute.toString()
                val endTimeStr = endTime.hour.toString() + ":" + endTime.minute.toString()
                Log.d("xxx", workout)
                var newWorkout = arrayOf(workout, startTimeStr, endTimeStr, id)
                replyIntent.putExtra(EXTRA_REPLY, newWorkout)
                Log.d("startime", startTimeStr)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.workoutListsql.REPLY"
        const val SEARCH_REPLY = "SEARCH.WORKOUT"
    }

}
