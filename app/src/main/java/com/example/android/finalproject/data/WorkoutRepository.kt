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
package com.example.android.finalproject.data

import androidx.annotation.WorkerThread
import com.example.android.finalproject.model.Workout
import kotlinx.coroutines.flow.Flow

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */
class WorkoutRepository(private val workoutDao: WorkoutDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allWords: Flow<List<Workout>> = workoutDao.getAlphabetizedWords()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(workout: Workout) {
        workoutDao.insert(workout)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(workout: Workout) {
        workoutDao.update(workout.workoutName, workout.startTime, workout.endTime, workout.id, workout.videoLink)
    }

    @WorkerThread
    suspend fun delete(id: Int) {
        workoutDao.deleteWorkout(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun search(id: Int) : Flow<Workout> {
        return workoutDao.getEntryById(id)
    }
}
