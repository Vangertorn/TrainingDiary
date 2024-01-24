package com.yankin.exercise_create.impl.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Training(
    val id: Long = 0L,
    val date: String,
    val muscleGroups: String? = null,
    val comment: String? = null,
    val weight: String? = null,
    val position: Int = 0,
    val deleted: Boolean = false,
    val selectedMuscleGroup: MutableList<Int> = mutableListOf()
) : Parcelable
