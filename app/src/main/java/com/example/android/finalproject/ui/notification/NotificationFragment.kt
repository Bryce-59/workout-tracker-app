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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.finalproject.databinding.FragmentNotificationBinding
import com.example.android.finalproject.model.notification.NotificationViewHolder
import com.example.android.finalproject.model.notification.NotificationViewModel
import com.example.android.finalproject.NotificationListAdapter

/**
 * Activity for entering a word.
 */

class NotificationFragment : Fragment(), NotificationViewHolder.OnItemClickListener {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private lateinit var notificationViewModel: NotificationViewModel

    private var adapter = NotificationListAdapter(this)

    private val newNotificationLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode != Activity.RESULT_OK) {
                Toast.makeText(
                    requireContext(),
                    "Notification cancelled",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            val intent = Intent(
                this@NotificationFragment.context,
                NewNotificationActivity::class.java
            )
            newNotificationLauncher.launch(intent)
        }

        // Add an observer on the LiveData returned by getAlarmsLiveData().
        // The observer fires when the observed data changes and the fragment is
        // in the foreground.
        val recyclerView = binding.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        notificationViewModel =
            ViewModelProvider(this)[NotificationViewModel::class.java]

        notificationViewModel.getAlarmsLiveData()?.observe(viewLifecycleOwner) { alarms ->
            if (alarms != null) {
                adapter.setAlarms(alarms)
            }
        }
    }

    override fun onItemClick(position: Int, view_code: Int) {
        val recyclerView = binding.recyclerview
        val viewHolder =
            recyclerView.findViewHolderForAdapterPosition(position) as? NotificationViewHolder
        val curNotification = viewHolder?.getNotification()

        if (view_code == 3 && curNotification != null) {
            notificationViewModel.delete(curNotification)
            Toast.makeText(
                this.context,
                "Notification deleted",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}