package com.yankin.storage.room.converters

import com.yankin.models.ExerciseDomain
import com.yankin.storage.room.entity.ExerciseEntity

fun ExerciseDomain.toEntity() = ExerciseEntity(
    id = id,
    name = name,
    idTraining = idTraining,
    position = position,
    comment = comment,
    deleted = deleted,
    idSet = idSet
)

fun ExerciseEntity.toModel() = ExerciseDomain(
    id = id,
    name = name,
    idTraining = idTraining,
    position = position,
    comment = comment,
    deleted = deleted,
    idSet = idSet
)