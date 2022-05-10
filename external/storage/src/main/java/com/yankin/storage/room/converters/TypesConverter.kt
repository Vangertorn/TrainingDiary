package com.yankin.storage.room.converters

import com.yankin.models.info.ViewHolderTypesDomain
import com.yankin.storage.room.entity.info.ViewHolderTypesEntity

fun ViewHolderTypesEntity.ExerciseInfo.toModel() = ViewHolderTypesDomain.ExerciseInfoDomain(
    exerciseDomain = exerciseEntity.toModel(),
    approachDomains = approaches?.map {
        it.toModel()
    }
)
