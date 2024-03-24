package com.yankin.training.impl.domain.models

import com.yankin.date.Timestamp

data class TrainingModel(
    val id: Long,
    val date: Timestamp.Milliseconds,
    val comment: String?,
    val personWeight: Double?,
    val selectedMuscleGroup: List<Long>,
)
