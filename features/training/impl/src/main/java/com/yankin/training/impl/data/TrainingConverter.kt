package com.yankin.training.impl.data

import com.yankin.room.entity.TrainingEntity
import com.yankin.training.api.models.TrainingDomain

fun TrainingDomain.toEntity() = TrainingEntity(
    id = id,
    date = date,
    muscleGroups = muscleGroups,
    comment = comment,
    weight = weight,
    position = position,
    deleted = deleted,
    selectedMuscleGroup = selectedMuscleGroup
)

fun TrainingEntity.toDomain() = TrainingDomain(
    id = id,
    date = date,
    muscleGroups = muscleGroups,
    comment = comment,
    weight = weight,
    position = position,
    deleted = deleted,
    selectedMuscleGroup = selectedMuscleGroup
)