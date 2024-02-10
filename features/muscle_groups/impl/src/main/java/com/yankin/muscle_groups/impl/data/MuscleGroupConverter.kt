package com.yankin.muscle_groups.impl.data

import com.yankin.room.entity.MuscleGroupEntity
import com.yankin.muscle_groups.api.models.MuscleGroupDomain

fun MuscleGroupEntity.toDomain() = MuscleGroupDomain(
    id = id,
    nameMuscleGroup = nameMuscleGroup,
    isDefault = isDefault,
    deleted = deleted,

)

fun MuscleGroupDomain.toEntity() = MuscleGroupEntity(
    id = id,
    nameMuscleGroup = nameMuscleGroup,
    isDefault = isDefault,
    deleted = deleted
)