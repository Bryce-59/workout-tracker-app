package com.example.android.finalproject.model.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.android.finalproject.model.user.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table ORDER BY date LIMIT 1")
    fun getCurrentData(): Flow<User>

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Query("SELECT * FROM user_table")
    fun getAllUserData(): Flow<List<User>>
}