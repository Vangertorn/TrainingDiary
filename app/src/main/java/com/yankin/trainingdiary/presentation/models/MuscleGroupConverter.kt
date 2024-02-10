package com.yankin.trainingdiary.presentation.models

import com.yankin.muscle_groups.api.models.MuscleGroupDomain

fun MuscleGroupDomain.toModel() = MuscleGroup(
    id = id,
    nameMuscleGroup = nameMuscleGroup,
    factorySettings = isDefault,
    deleted = deleted
)

fun MuscleGroup.toDomain() = MuscleGroupDomain(
    id = id,
    nameMuscleGroup = nameMuscleGroup,
    isDefault = factorySettings,
    deleted = deleted
)