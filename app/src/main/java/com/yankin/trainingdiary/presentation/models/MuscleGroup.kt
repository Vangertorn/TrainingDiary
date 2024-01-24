package com.yankin.trainingdiary.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MuscleGroup(
    val id: Long = 0,
    val nameMuscleGroup: String,
    val factorySettings: Boolean,
    val deleted: Boolean = false
) : Parcelable
