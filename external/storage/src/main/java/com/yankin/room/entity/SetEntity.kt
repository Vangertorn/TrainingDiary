package com.yankin.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "table_set",
    foreignKeys = [
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("idExercise"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)

data class SetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val weight: Double,
    val reps: Int,
    @ColumnInfo(index = true, name = "idExercise")
    val idExercise: Long
)
