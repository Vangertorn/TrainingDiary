package com.yankin.training_create.impl.presentation

import com.yankin.muscle_groups.api.models.MuscleGroupDomain

fun MuscleGroupDomain.toModel() = MuscleGroup(
    id = id,
    nameMuscleGroup = nameMuscleGroup,
    factorySettings = factorySettings,
    deleted = deleted
)

fun MuscleGroup.toDomain() = MuscleGroupDomain(
    id = id,
    nameMuscleGroup = nameMuscleGroup,
    factorySettings = factorySettings,
    deleted = deleted
)