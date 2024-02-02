package com.yankin.exercise_list.impl.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class ExerciseListParcelableParams(
    val trainingId: Long,
    val date: String,
    val muscleGroups: String?,
    val comment: String?,
    val weight: String?,
    val position: Int,
    val deleted: Boolean,
    val selectedMuscleGroup: List<Int>,
) : Parcelable