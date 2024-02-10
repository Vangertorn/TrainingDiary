package com.yankin.settings.impl.presentation.settings.models

import com.yankin.common.recyclerview.adapter.UiItem

internal data class SettingsUiState(
    val reps: String,
    val weight: String,
    val isLastTrainingTop: Boolean,
    val trainingPositionDescription: String,
    val muscleGroupList: List<UiItem>,
    val muscleGroupNameByUser: String,
)