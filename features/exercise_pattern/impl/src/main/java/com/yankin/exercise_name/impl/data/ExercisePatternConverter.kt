package com.yankin.exercise_name.impl.data

import com.yankin.exercise_pattern.api.models.ExercisePatternDomain
import com.yankin.room.entity.ExercisePatternEntity

fun ExercisePatternEntity.toDomain() = ExercisePatternDomain(
    id = id,
    name = name
)

fun ExercisePatternDomain.toEntity() = ExercisePatternEntity(
    id = id,
    name = name
)