package com.yankin.training.api.models

import com.yankin.date.Timestamp
import com.yankin.muscle_groups.api.models.MuscleGroupDomain

data class TrainingDomain(
    val id: Long,
    val date: Timestamp.Milliseconds,
    val comment: String?,
    val personWeight: Double?,
    val selectedMuscleGroup: List<MuscleGroupDomain>,
)
