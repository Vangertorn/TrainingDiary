package com.yankin.storage.room.converters

import com.yankin.models.info.SuperSetInfoDomain
import com.yankin.storage.room.entity.info.SuperSetInfoEntity

fun SuperSetInfoEntity.toModel() = SuperSetInfoDomain(
    superSetDomain = superSetEntity.toModel(),
    exerciseDomain = exerciseEntity?.map {
        it.toModel()
    }
)