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

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.finalproject.*
import com.example.android.finalproject.databinding.FragmentNotificationBinding
import com.example.android.finalproject.model.notification.Notification
import com.example.android.finalproject.model.notification.NotificationViewHolder
import com.example.android.finalproject.model.notification.NotificationViewModel
/**
 * Activity for entering a word.
 */

class NotificationFragment : Fragment(), NotificationViewHolder.OnItemClickListener {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private val newNotiActivityRequestCode = 1
    private lateinit var notificationViewModel: NotificationViewModel

    private var adapter = NotificationListAdapter(this)

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
        notificationViewModel = ViewModelProvider(this)[NotificationViewModel::class.java]
        notificationViewModel.getAlarmsLiveData()?.observe(viewLifecycleOwner,
            Observer<List<Any?>?> { alarms ->
                if (alarms != null) {
                    adapter.setAlarms(alarms as List<Notification?>)
                }
            })
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.notificationListsql.REPLY"
        const val SEARCH_REPLY = "SEARCH.NOTIFICATION"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode != newNotiActivityRequestCode || resultCode != Activity.RESULT_OK) {
            Toast.makeText(
                this.context,
                "Notification cancelled",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onItemClick(position: Int, view_code: Int) {
        val recyclerView = binding.recyclerview
        var viewHolder: NotificationViewHolder =
            recyclerView.findViewHolderForAdapterPosition(position) as NotificationViewHolder;
        val curNotification = viewHolder.getNotification()

        if (view_code == 3 && curNotification != null){
            notificationViewModel.delete(curNotification)
            Toast.makeText(
                this.context,
                "Notification deleted",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}