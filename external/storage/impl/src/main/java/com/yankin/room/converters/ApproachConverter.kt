package com.yankin.room.converters

import com.yankin.models.ApproachDomain
import com.yankin.room.entity.ApproachEntity

fun ApproachDomain.toEntity() = ApproachEntity(
    id = id,
    weight = weight,
    reoccurrences = reoccurrences,
    idExercise = idExercise
)

fun ApproachEntity.toModel() = ApproachDomain(
    id = id,
    weight = weight,
    reoccurrences = reoccurrences,
    idExercise = idExercise
)