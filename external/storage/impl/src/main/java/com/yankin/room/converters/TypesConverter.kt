package com.yankin.room.converters

import com.yankin.models.info.ViewHolderTypesDomain
import com.yankin.room.entity.info.ViewHolderTypesEntity

fun ViewHolderTypesEntity.ExerciseInfo.toModel() = ViewHolderTypesDomain.ExerciseInfoDomain(
    exerciseDomain = exerciseEntity.toModel(),
    approachDomains = approaches?.map {
        it.toModel()
    }
)
