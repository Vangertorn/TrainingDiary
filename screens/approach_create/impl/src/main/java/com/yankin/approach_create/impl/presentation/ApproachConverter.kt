package com.yankin.approach_create.impl.presentation

import com.yankin.approach.api.models.ApproachDomain

fun Approach.toDomain() = ApproachDomain(
    id = id,
    weight = weight,
    repeat = reoccurrences,
    idExercise = idExercise
)

fun ApproachDomain.toModel() = Approach(
    id = id,
    weight = weight,
    reoccurrences = repeat,
    idExercise = idExercise
)