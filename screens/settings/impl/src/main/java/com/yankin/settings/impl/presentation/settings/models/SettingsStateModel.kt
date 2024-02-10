package com.yankin.settings.impl.presentation.settings.models

import com.yankin.muscle_groups.api.models.MuscleGroupDomain

internal data class SettingsStateModel(
    val reps: Int,
    val weight: Double,
    val isLastTrainingTop: Boolean,
    val muscleGroupList: List<MuscleGroupDomain>,
    val muscleGroupNameByUser: String,
)