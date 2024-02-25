package com.yankin.approach_create.impl.presentation.models

import com.yankin.common.recyclerview.adapter.UiItem

internal data class SetCreateUiState(
    val exerciseName: String,
    val exerciseComment: String,
    val exerciseList: List<UiItem>,
    val setList: List<UiItem>,
    val autoCompleteExercise: List<String>,
    val weight: String,
    val reps: String,
)