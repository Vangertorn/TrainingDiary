package com.yankin.trainingdiary.models.converters

import com.yankin.models.info.ViewHolderTypesDomain
import com.yankin.trainingdiary.models.info.ViewHolderTypes

fun ViewHolderTypesDomain.ExerciseInfoDomain.toModel() = ViewHolderTypes.ExerciseInfo(
    exercise = exerciseDomain.toModel(),
    approaches = approachDomains?.map {
        it.toModel()
    }
)