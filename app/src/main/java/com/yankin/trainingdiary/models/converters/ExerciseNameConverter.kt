package com.yankin.trainingdiary.models.converters

import com.yankin.exercese_name.api.models.ExerciseNameDomain
import com.yankin.trainingdiary.models.ExerciseName

fun ExerciseNameDomain.toModel() = ExerciseName(
    id = id,
    nameExercise = nameExercise
)

fun ExerciseName.toDomain() = ExerciseNameDomain(
    id = id,
    nameExercise = nameExercise
)