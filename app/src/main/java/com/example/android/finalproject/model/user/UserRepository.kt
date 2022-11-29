package com.example.android.finalproject.data

import androidx.annotation.WorkerThread
import com.example.android.finalproject.model.user.User
import com.example.android.finalproject.model.user.UserDao
import com.example.android.finalproject.model.workout.Workout
import com.example.android.finalproject.model.workout.WorkoutDao
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao, private val workoutDao: WorkoutDao) {

    val currentUserData: Flow<User> = userDao.getCurrentData()

    val userHistory: Flow<List<User>> = userDao.getAllUserData()

    val workoutHistory: Flow<List<Workout>> = workoutDao.getAlphabetizedWords()


    @WorkerThread
    suspend fun insert(user: User){
        userDao.insert(user)
    }

    @WorkerThread
    suspend fun update(user: User) {
        userDao.update(user)
    }
}