package com.yankin.trainingdiary.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "table_exercise_autofill", indices = [androidx.room.Index(
        value = ["nameExercise"],
        unique = true
    )]
)
data class ExerciseAutofill(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nameExercise: String
) : Parcelable