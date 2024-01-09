package com.yankin.trainingdiary.models.converters

import com.yankin.super_set.api.models.SuperSetDomain
import com.yankin.trainingdiary.models.SuperSet

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