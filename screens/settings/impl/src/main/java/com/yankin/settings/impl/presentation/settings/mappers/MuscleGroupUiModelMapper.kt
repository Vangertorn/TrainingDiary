package com.yankin.settings.impl.presentation.settings.mappers

import com.yankin.muscle_groups.api.models.MuscleGroupDomain
import com.yankin.settings.impl.presentation.settings.adapter.MuscleGroupUiModel

internal fun MuscleGroupDomain.toMuscleGroupUiModel(): MuscleGroupUiModel {
    return MuscleGroupUiModel(
        description = nameMuscleGroup,
        muscleGroupId = id,
    )
}