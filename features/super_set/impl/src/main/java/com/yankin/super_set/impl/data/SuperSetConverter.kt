package com.yankin.super_set.impl.data

import com.yankin.super_set.api.models.SuperSetDomain
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