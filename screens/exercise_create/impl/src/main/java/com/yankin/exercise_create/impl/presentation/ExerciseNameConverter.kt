package com.yankin.exercise_create.impl.presentation

import com.yankin.exercise_pattern.api.models.ExercisePatternDomain

fun ExercisePatternDomain.toModel() = exercisePattern(
    id = id,
    nameExercise = name
)

fun exercisePattern.toDomain() = ExercisePatternDomain(
    id = id,
    name = nameExercise
)