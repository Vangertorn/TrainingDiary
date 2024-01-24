package com.yankin.settings.impl.presentation

import com.yankin.exercese_name.api.models.ExerciseNameDomain

fun ExerciseNameDomain.toModel() = ExerciseName(
    id = id,
    nameExercise = nameExercise
)

fun ExerciseName.toDomain() = ExerciseNameDomain(
    id = id,
    nameExercise = nameExercise
)