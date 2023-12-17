package com.yankin.exercise_name.impl.data

import com.yankin.exercese_name.api.models.ExerciseNameDomain
import com.yankin.room.entity.ExerciseNameEntity

fun ExerciseNameEntity.toModel() = ExerciseNameDomain(
    id = id,
    nameExercise = nameExercise
)

fun ExerciseNameDomain.toEntity() = ExerciseNameEntity(
    id = id,
    nameExercise = nameExercise
)