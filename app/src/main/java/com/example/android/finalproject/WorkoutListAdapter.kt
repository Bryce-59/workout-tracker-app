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
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.finalproject.WordListAdapter.WordViewHolder

class WordListAdapter(private val listener: OnItemClickListener) : ListAdapter<Workout, WordViewHolder>(WORDS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent, listener)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind("Name: " + current.workoutName + "\n" +
                "Start Time: " + current.startTime + "\n" +
                "End Time: " + current.endTime)
    }

    class WordViewHolder(itemView: View, nodeLister: OnItemClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val wordItemView: TextView = itemView.findViewById(R.id.workoutView)
        private val listener: OnItemClickListener = nodeLister
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position : Int = adapterPosition
            if (position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }

        fun bind(text: String?) {
            wordItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup, listener: OnItemClickListener): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return WordViewHolder(view, listener)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<Workout>() {
            override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
