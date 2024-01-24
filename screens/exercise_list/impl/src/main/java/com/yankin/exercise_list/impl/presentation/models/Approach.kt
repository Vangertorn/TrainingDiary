package com.yankin.exercise_list.impl.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Approach(
    val id: Long = 0,
    val weight: String = "0",
    val reoccurrences: String = "0",
    val idExercise: Long
) : Parcelable
