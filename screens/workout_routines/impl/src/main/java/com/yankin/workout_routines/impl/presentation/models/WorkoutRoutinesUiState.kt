package com.yankin.workout_routines.impl.presentation.models

import com.yankin.common.recyclerview.adapter.UiItem

internal data class WorkoutRoutinesUiState(
    val trainingDate: String,
    val trainingMuscleGroups: String,
    val trainingWeight: String,
    val trainingComment: String,
    val exercises: List<UiItem>,
)