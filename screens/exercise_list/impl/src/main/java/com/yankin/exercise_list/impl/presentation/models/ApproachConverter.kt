package com.yankin.exercise_list.impl.presentation.models

import com.yankin.set.api.models.SetDomain

fun Approach.toDomain() = SetDomain(
    id = id,
    weight = weight.toDouble(),
    reps = reoccurrences.toInt(),
    idExercise = idExercise
)

fun SetDomain.toModel() = Approach(
    id = id,
    weight = weight.toString(),
    reoccurrences = reps.toString(),
    idExercise = idExercise
)