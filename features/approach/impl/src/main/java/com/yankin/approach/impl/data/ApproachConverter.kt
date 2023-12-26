package com.yankin.approach.impl.data

import com.yankin.approach.api.models.ApproachDomain
import com.yankin.room.entity.ApproachEntity

fun ApproachDomain.toEntity() = ApproachEntity(
    id = id,
    weight = weight,
    repeat = repeat,
    idExercise = idExercise
)

fun ApproachEntity.toDomain() = ApproachDomain(
    id = id,
    weight = weight,
    repeat = repeat,
    idExercise = idExercise
)