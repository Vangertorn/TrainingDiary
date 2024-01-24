package com.yankin.exercise_create.impl.presentation

import com.yankin.super_set.api.models.SuperSetDomain

fun SuperSetDomain.toModel() = SuperSet(
    id = id,
    idTraining = idTraining,
    deleted = deleted,
    visibility = visibility,
    position = position
)

fun SuperSet.toDomain() = SuperSetDomain(
    id = id,
    idTraining = idTraining,
    deleted = deleted,
    visibility = visibility,
    position = position
)