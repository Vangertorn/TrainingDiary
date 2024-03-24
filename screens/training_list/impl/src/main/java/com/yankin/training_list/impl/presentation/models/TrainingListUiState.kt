package com.yankin.training_list.impl.presentation.models

import com.yankin.common.recyclerview.adapter.UiItem

internal data class TrainingListUiState (
    val daysLeft: String,
    val trainingLeft: String,
    val trainings: List<UiItem>,
    val scrollToUp: Boolean,
    val scrollToBottom: Boolean,
)