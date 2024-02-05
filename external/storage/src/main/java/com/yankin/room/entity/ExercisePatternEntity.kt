package com.yankin.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "table_exercise_pattern",
    indices = [
        androidx.room.Index(
            value = ["name"],
            unique = true,
        )
    ]
)
data class ExercisePatternEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
)
