package com.yankin.exercise_create.impl.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class ExerciseTemporary(
    val id: Int,
    val name: String,
    val comment: String?,
) : Parcelable