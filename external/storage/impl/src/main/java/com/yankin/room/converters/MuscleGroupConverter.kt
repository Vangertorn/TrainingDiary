package com.yankin.room.converters

import com.yankin.models.MuscleGroupDomain
import com.yankin.room.entity.MuscleGroupEntity

fun MuscleGroupEntity.toModel() = MuscleGroupDomain(
    id = id,
    nameMuscleGroup = nameMuscleGroup,
    factorySettings = factorySettings,
    deleted = deleted
)

fun MuscleGroupDomain.toEntity() = MuscleGroupEntity(
    id = id,
    nameMuscleGroup = nameMuscleGroup,
    factorySettings = factorySettings,
    deleted = deleted
)