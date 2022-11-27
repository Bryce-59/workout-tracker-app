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
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.finalproject.*
import com.example.android.finalproject.model.WorkoutsApplication
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.android.finalproject.databinding.FragmentNotificationBinding
import com.example.android.finalproject.model.workout.Notification

/**
 * Activity for entering a word.
 */

class NotificationFragment : Fragment(), NotiListAdapter.OnItemClickListener {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private val newNotiActivityRequestCode = 1
    private val repalceNotiActivityRequestCode = 2

    private val notificationViewModel: NotificationViewModel by viewModels {
        NotiViewModelFactory((activity?.application as WorkoutsApplication).repositoryN)
    }
    private var adapter = NotiListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            val intent = Intent(this@NotificationFragment.context, NewNotificationActivity::class.java)
            startActivityForResult(intent, newNotiActivityRequestCode)
        }

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        val recyclerView = binding.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        notificationViewModel.allWords.observe(viewLifecycleOwner) { words ->
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
        Log.d("myTag", requestCode.toString())
        Log.d("myTag", Activity.RESULT_OK.toString())

        if (requestCode == newNotiActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getStringArrayExtra(NewNotificationActivity.EXTRA_REPLY)?.let { reply ->
                val notification =  Notification(0, reply[0], reply[1], false)
                notificationViewModel.insert(notification)
            }
        } else if (requestCode == repalceNotiActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.getStringArrayExtra(NewWorkoutActivity.EXTRA_REPLY)?.let { reply ->
                val notification = Notification(reply[3].toInt(), reply[0], reply[1], false)
                notificationViewModel.update(notification)
            }
        } else {
            Toast.makeText(
                this.context,
                "Notification not saved because it was empty",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onItemClick(position: Int, view_code: Int) {

        val recyclerView = binding.recyclerview
        var viewHolder: NotiListAdapter.NotiViewHolder? =
            recyclerView.findViewHolderForAdapterPosition(position) as NotiListAdapter.NotiViewHolder;
        val curNotification = viewHolder?.notification

        var notificationInfo = curNotification?.let { arrayOf(it.day_of_week, it.start_time, it.weekly) }
        if (notificationInfo != null) {
            Log.d("myTag", ""/*notificationInfo[0]*/)
        };

        if (view_code == 0) {
            val intent = Intent(this@NotificationFragment.context, NewWorkoutActivity::class.java)
            intent.putExtra(NewNotificationActivity.SEARCH_REPLY, notificationInfo)
            startActivity(intent);
        } else if (view_code == 3){
            notificationViewModel.delete(curNotification!!.id)
        }
    }
}