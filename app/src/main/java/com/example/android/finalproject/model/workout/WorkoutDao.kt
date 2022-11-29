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

package com.example.android.finalproject.model.workout

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.finalproject.model.workout.Workout
import kotlinx.coroutines.flow.Flow

/**
 * The Room Magic is in this file, where you map a method call to an SQL query.
 *
 * When you are using complex data types, such as Date, you have to also supply type converters.
 * To keep this example basic, no types that require type converters are used.
 * See the documentation at
 * https://developer.android.com/topic/libraries/architecture/room.html#type-converters
 */

@Dao
interface WorkoutDao {

    // The flow always holds/caches latest version of data. Notifies its observers when the
    // data has changed.
    @Query("SELECT * FROM workout_table ORDER BY id ASC")
    fun getAlphabetizedWords(): Flow<List<Workout>>

    @Query("SELECT * FROM workout_table WHERE id = :index LIMIT 1")
    fun getEntryById(index:Int): Flow<Workout>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(workout: Workout)
    @Query("UPDATE workout_table SET workoutName=:workoutName, startTime=:startTime, endTime=:EndTime, date=:date, videoLink=:videoLink, calories=:calories WHERE id = :index")
    suspend fun update(workoutName: String, startTime:String, EndTime:String, videoLink: String, index: Int, date:String, calories: Int)
    @Query("DELETE FROM workout_table")
    suspend fun deleteAll()
    @Query("DELETE FROM workout_table where id=:id")
    suspend fun deleteWorkout(id: Int)
}
