package com.yankin.approach_create.impl.presentation

import com.yankin.set.api.models.SetDomain

fun Approach.toDomain() = SetDomain(
    id = id,
    weight = weight.toDouble(),
    repeat = reoccurrences.toInt(),
    idExercise = idExercise
)

fun SetDomain.toModel() = Approach(
    id = id,
    weight = weight.toString(),
    reoccurrences = repeat.toString(),
    idExercise = idExercise
)