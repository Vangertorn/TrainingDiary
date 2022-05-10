package com.yankin.storage.room.converters

import com.yankin.models.ExerciseAutofillDomain
import com.yankin.storage.room.entity.ExerciseAutofillEntity

fun ExerciseAutofillEntity.toModel() = ExerciseAutofillDomain(
    id = id,
    nameExercise = nameExercise
)

fun ExerciseAutofillDomain.toEntity() = ExerciseAutofillEntity(
    id = id,
    nameExercise = nameExercise
)