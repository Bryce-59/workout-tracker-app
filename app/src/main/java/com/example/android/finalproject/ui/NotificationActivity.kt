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
import android.util.Log
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.finalproject.*
import com.example.android.finalproject.databinding.ActivityNotificationBinding
import com.example.android.finalproject.model.workout.Notification
import com.example.android.finalproject.model.WorkoutsApplication
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * Activity for entering a word.
 */

class NotificationActivity : AppCompatActivity(), NotiListAdapter.OnItemClickListener {
    private lateinit var binding: ActivityNotificationBinding
    private val newNotiActivityRequestCode = 1
    private val notificationViewModel: NotificationViewModel by viewModels {
        NotiViewModelFactory((application as WorkoutsApplication).repositoryN)
    }
    private var adapter = NotiListAdapter(this)

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview_not)
        adapter = NotiListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@NotificationActivity, NewNotificationActivity::class.java)
            startActivityForResult(intent, newNotiActivityRequestCode)
        }

        val button = findViewById<FloatingActionButton>(R.id.button_back)
        button.setOnClickListener {
            finish()
        }

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        notificationViewModel.allWords.observe(owner = this) { words ->
            // Update the cached copy of the words in the adapter.
            words.let { adapter.submitList(it) }
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.notificationListsql.REPLY"
        const val SEARCH_REPLY = "SEARCH.NOTIFICATION"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newNotiActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getStringArrayExtra(NewNotificationActivity.EXTRA_REPLY)?.let { reply ->
                val notification =  Notification(0, reply[0], reply[1], false)
                notificationViewModel.insert(notification)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onItemClick(position: Int, view_code: Int) {

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview_not)
        var viewHolder: NotiListAdapter.NotiViewHolder? =
            recyclerView.findViewHolderForAdapterPosition(position) as NotiListAdapter.NotiViewHolder;
        val curNotification = viewHolder?.notification

        var notificationInfo = curNotification?.let { arrayOf(it.day_of_week, it.start_time, it.weekly) }
        if (notificationInfo != null) {
            Log.d("myTag", ""/*notificationInfo[0]*/)
        };

        if (view_code == 0) {
            val intent = Intent(this@NotificationActivity, NewNotificationActivity::class.java)
            intent.putExtra(NewNotificationActivity.SEARCH_REPLY, notificationInfo)
            startActivity(intent);
        } else if (view_code == 3){
            notificationViewModel.delete(curNotification!!.id)
        }
    }

}