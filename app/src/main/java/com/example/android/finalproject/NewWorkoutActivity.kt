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
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/**
 * Activity for entering a word.
 */

class NewWorkoutActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_workout)
        val workoutName = findViewById<EditText>(R.id.workout_name)

        var workoutInfo : Array<out String>? = intent?.getStringArrayExtra(SEARCH_REPLY)

        if (workoutInfo != null) {
            if (workoutInfo.size == 3)

                workoutName.setText(workoutInfo[0], TextView.BufferType.EDITABLE)
        }

        val startTime = findViewById<TimePicker>(R.id.startTimePicker)
        val endTime = findViewById<TimePicker>(R.id.endTimePicker)


        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(workoutName.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val workout = workoutName.text.toString()
                val startTimeStr = startTime.hour.toString() + ":" + startTime.minute.toString()
                val endTimeStr = endTime.hour.toString() + ":" + endTime.minute.toString()

                var workoutInfo = arrayOf(workout, startTimeStr, endTimeStr)
                replyIntent.putExtra(EXTRA_REPLY, workoutInfo)
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
