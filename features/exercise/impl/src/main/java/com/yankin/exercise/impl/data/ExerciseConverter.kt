package com.yankin.exercise.impl.data

import com.yankin.exercise.api.models.ExerciseDomain
import com.yankin.room.entity.ExerciseEntity

fun ExerciseDomain.toEntity() = ExerciseEntity(
    id = id,
    name = name,
    trainingBlockId = trainingBlockId,
    position = position,
    comment = comment,
)

fun ExerciseEntity.toDomain() = ExerciseDomain(
    id = id,
    name = name,
    trainingBlockId = trainingBlockId,
    position = position,
    comment = comment,
)