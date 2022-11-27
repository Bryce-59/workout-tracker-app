package com.example.android.finalproject.data

import com.example.android.finalproject.model.user.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    val currentUserData: Flow<User> = userDao.getCurrentData()
}