package com.yankin.room.converters

import com.yankin.models.ExerciseAutofillDomain
import com.yankin.room.entity.ExerciseAutofillEntity

fun ExerciseAutofillEntity.toModel() = ExerciseAutofillDomain(
    id = id,
    nameExercise = nameExercise
)

fun ExerciseAutofillDomain.toEntity() = ExerciseAutofillEntity(
    id = id,
    nameExercise = nameExercise
)