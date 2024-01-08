package com.yankin.room.converters

import com.yankin.exercise.impl.data.toModel
import com.yankin.models.info.SuperSetInfoDomain
import com.yankin.room.entity.info.SuperSetInfoEntity

fun SuperSetInfoEntity.toModel() = SuperSetInfoDomain(
    superSetDomain = superSetEntity.toModel(),
    exerciseDomain = exerciseEntity?.map {
        it.toModel()
    }
)