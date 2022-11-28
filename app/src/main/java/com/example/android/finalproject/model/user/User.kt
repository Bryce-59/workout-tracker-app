package com.example.android.finalproject.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.text.DateFormat
import java.util.*

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val weight: Double,
    val height: String,
    val calories: Double,
    val distance: Double,
    val workoutTime: Int,
    val date: String,
)


