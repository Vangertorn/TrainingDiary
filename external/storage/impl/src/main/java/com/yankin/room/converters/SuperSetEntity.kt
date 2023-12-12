package com.yankin.room.converters

import com.yankin.models.SuperSetDomain
import com.yankin.room.entity.SuperSetEntity

fun SuperSetEntity.toModel() = SuperSetDomain(
    id = id,
    idTraining = idTraining,
    deleted = deleted,
    visibility = visibility,
    position = position
)

fun SuperSetDomain.toEntity() = SuperSetEntity(
    id = id,
    idTraining = idTraining,
    deleted = deleted,
    visibility = visibility,
    position = position
)