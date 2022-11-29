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

package com.example.android.finalproject.model.notification

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notification_table")
data class Notification(
    @PrimaryKey(autoGenerate = true)
    val notiId: Int,

    val hour: Int,
    val minute: Int,

    val time_created: Long,

    var active: Boolean,

    val sun: Boolean,
    val mon: Boolean,
    val tues: Boolean,
    val wed: Boolean,
    val thurs: Boolean,
    val fri: Boolean,
    val sat: Boolean,

    val weekly: Boolean,
) {

    fun getRecurringDaysText(): String? {
        if (!active) {
            return null
        }

        var days = ""
        if (sun) {
            days += "Su"
        }
        if (mon) {
            days += "M"
        }
        if (tues) {
            days += "Tu"
        }
        if (wed) {
            days += "We"
        }
        if (thurs) {
            days += "Th"
        }
        if (fri) {
            days += "F"
        }
        if (sat) {
            days += "Sa"
        }
        val none = sun || mon || tues || wed || thurs || fri || sat
        if (!none) {
            days = "Today"
        }
        return days
    }
}
