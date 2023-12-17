package com.yankin.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "table_exercise_name",
    indices = [
        androidx.room.Index(
            value = ["nameExercise"],
            unique = true
        )
    ]
)
data class ExerciseNameEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nameExercise: String
)
