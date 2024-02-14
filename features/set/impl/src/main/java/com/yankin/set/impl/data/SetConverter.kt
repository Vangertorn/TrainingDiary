package com.yankin.set.impl.data

import com.yankin.set.api.models.SetDomain
import com.yankin.room.entity.SetEntity

fun SetDomain.toEntity() = SetEntity(
    id = id,
    weight = weight,
    reps = repeat,
    idExercise = idExercise
)

fun SetEntity.toDomain() = SetDomain(
    id = id,
    weight = weight,
    repeat = reps,
    idExercise = idExercise
)