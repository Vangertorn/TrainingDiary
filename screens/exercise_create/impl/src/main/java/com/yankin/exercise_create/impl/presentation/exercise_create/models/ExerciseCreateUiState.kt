package com.yankin.exercise_create.impl.presentation.exercise_create.models

import com.yankin.common.recyclerview.adapter.UiItem

internal data class ExerciseCreateUiState(
    val exerciseName: String,
    val exerciseComment: String,
    val superSetList: List<UiItem>,
    val actionButtonDescription: String,
    val autoCompleteExercise: List<String>,
)