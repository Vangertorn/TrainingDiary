package com.yankin.trainingdiary.models.converters

import com.yankin.approach.api.models.ApproachDomain
import com.yankin.trainingdiary.models.Approach

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