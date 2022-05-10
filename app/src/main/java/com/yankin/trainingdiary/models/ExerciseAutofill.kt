package com.yankin.trainingdiary.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseAutofill(
    val id: Long = 0,
    val nameExercise: String
) : Parcelable
