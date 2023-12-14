package com.yankin.trainingdiary.models.converters

import com.yankin.domain.api.model.MuscleGroupDomain
import com.yankin.trainingdiary.models.MuscleGroup

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