package com.yankin.exercise.impl.data

import com.yankin.exercise.api.models.ExerciseDomain
import com.yankin.room.entity.ExerciseEntity

fun ExerciseDomain.toEntity() = ExerciseEntity(
    id = id,
    name = name,
    idTraining = idTraining,
    position = position,
    comment = comment,
    deleted = deleted,
    idSet = idSet
)

fun ExerciseEntity.toDomain() = ExerciseDomain(
    id = id,
    name = name,
    idTraining = idTraining,
    position = position,
    comment = comment,
    deleted = deleted,
    idSet = idSet,
)