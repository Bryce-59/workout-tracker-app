package com.example.android.finalproject.data

import androidx.annotation.WorkerThread
import com.example.android.finalproject.model.user.User
import com.example.android.finalproject.model.user.UserDao
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    val currentUserData: Flow<User> = userDao.getCurrentData()

    val userHistory: Flow<List<User>> = userDao.getAllUserData()

    @WorkerThread
    suspend fun insert(user: User){
        userDao.insert(user)
    }

    @WorkerThread
    suspend fun update(user: User) {
        userDao.update(user)
    }
}