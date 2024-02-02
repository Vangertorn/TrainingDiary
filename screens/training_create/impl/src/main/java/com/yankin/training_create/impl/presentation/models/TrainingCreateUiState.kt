package com.yankin.training_create.impl.presentation.models

import com.yankin.training_create.impl.presentation.adapter.MuscleGroupUiModel
import java.util.Date

internal data class TrainingCreateUiState(
    val muscleGroupList: List<MuscleGroupUiModel>,
    val weight: String,
    val comment: String,
    val selectedDate: Date,
)