package com.yankin.exercise_list.impl.presentation.models

import com.yankin.exercise.api.models.ExerciseDomain

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