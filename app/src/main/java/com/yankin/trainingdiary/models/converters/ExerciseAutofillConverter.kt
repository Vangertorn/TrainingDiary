package com.yankin.trainingdiary.models.converters

import com.yankin.models.ExerciseAutofillDomain
import com.yankin.trainingdiary.models.ExerciseAutofill

fun ExerciseAutofillDomain.toModel() = ExerciseAutofill(
    id = id,
    nameExercise = nameExercise
)

fun ExerciseAutofill.toDomain() = ExerciseAutofillDomain(
    id = id,
    nameExercise = nameExercise
)