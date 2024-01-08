package com.yankin.trainingdiary.models.converters

import com.yankin.exercise.api.models.ExerciseDomain
import com.yankin.trainingdiary.models.Exercise

fun Exercise.toDomain() = ExerciseDomain(
    id = id,
    name = name,
    idTraining = idTraining,
    position = position,
    comment = comment,
    deleted = deleted,
    idSet = idSet
)

fun ExerciseDomain.toModel() = Exercise(
    id = id,
    name = name,
    idTraining = idTraining,
    position = position,
    comment = comment,
    deleted = deleted,
    idSet = idSet
)