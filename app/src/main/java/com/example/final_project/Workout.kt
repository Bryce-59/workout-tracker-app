package com.example.final_project

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_table")
data class Workout(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "workout") val workout: String)