package com.yankin.exercise_list.impl.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SuperSet(
    val id: Long = 0,
    val idTraining: Long,
    val deleted: Boolean = false,
    val visibility: Boolean = false,
    val position: Int = 0
) : Parcelable
