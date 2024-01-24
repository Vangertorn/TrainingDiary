package com.yankin.approach_create.impl.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Approach(
    val id: Long = 0,
    val weight: String = "0",
    val reoccurrences: String = "0",
    val idExercise: Long
) : Parcelable
