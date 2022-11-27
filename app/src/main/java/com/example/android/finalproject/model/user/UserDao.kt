package com.example.android.finalproject.data

import androidx.room.Dao
import androidx.room.Query
import com.example.android.finalproject.model.user.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user_data " +
            "WHERE date = " +
            "(SELECT MAX(date) FROM user_data)")
    fun getCurrentData(): Flow<User>

    @Query("DELETE FROM user_data")
    suspend fun deleteAll()
}