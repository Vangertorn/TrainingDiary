package com.yankin.trainingdiary.models.converters

import com.yankin.models.ApproachDomain
import com.yankin.trainingdiary.models.Approach

fun Approach.toDomain() = ApproachDomain(
    id = id,
    weight = weight,
    reoccurrences = reoccurrences,
    idExercise = idExercise
)

fun ApproachDomain.toModel() = Approach(
    id = id,
    weight = weight,
    reoccurrences = reoccurrences,
    idExercise = idExercise
)