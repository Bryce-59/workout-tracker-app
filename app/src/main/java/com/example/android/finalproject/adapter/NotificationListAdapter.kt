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

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.finalproject.model.workout.Notification

class NotiListAdapter(private val listener: OnItemClickListener) : ListAdapter<Notification, NotiListAdapter.NotiViewHolder>(NOTIS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotiViewHolder {
        return NotiViewHolder.create(parent, listener)
    }

    override fun onBindViewHolder(holder: NotiViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind("Day: " + current.day_of_week + "\n" +
                "Time: " + current.start_time + "\n" +
                "Repeated? " + current.weekly, current)
    }

    class NotiViewHolder(itemView: View, nodeLister: OnItemClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val notiItemView: TextView = itemView.findViewById(R.id.workoutView)
        private val deleteImage: ImageView = itemView.findViewById(R.id.delete )
        private val listener: OnItemClickListener = nodeLister
        var notification: Notification = Notification(0,"", "", false)
        init {
            itemView.setOnClickListener(this)
            deleteImage.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            var view_code : Int = 0
            if (v != null) {
                if (v.id == deleteImage.id){
                    view_code = 3
                }else{
                    view_code = 0
                }
            }
            val position : Int = adapterPosition
            if (position != RecyclerView.NO_POSITION){
                listener.onItemClick(position, view_code)
            }
        }

        fun bind(text: String?, notification: Notification) {
            this.notification = notification
            notiItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup, listener: OnItemClickListener): NotiViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_notification, parent, false)
                return NotiViewHolder(view, listener)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int, view_code: Int)

    }

    companion object {
        private val NOTIS_COMPARATOR = object : DiffUtil.ItemCallback<Notification>() {
            override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
