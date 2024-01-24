package com.yankin.exercise_list.impl.presentation.models

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