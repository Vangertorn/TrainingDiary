package com.yankin.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "table_exercise_autofill",
    indices = [
        androidx.room.Index(
            value = ["nameExercise"],
            unique = true
        )
    ]
)
data class ExerciseAutofillEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nameExercise: String
)
