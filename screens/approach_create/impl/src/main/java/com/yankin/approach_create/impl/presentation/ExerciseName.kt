package com.yankin.approach_create.impl.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class exercisePattern(
    val id: Long = 0,
    val nameExercise: String
) : Parcelable
