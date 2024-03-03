package com.yankin.training_exercises.impl.presentation.models

import com.yankin.common.recyclerview.adapter.UiItem

internal data class TrainingExercisesUiState(
    val trainingDate: String,
    val trainingMuscleGroups: String,
    val trainingWeight: String,
    val trainingComment: String,
    val exercises: List<UiItem>,
)