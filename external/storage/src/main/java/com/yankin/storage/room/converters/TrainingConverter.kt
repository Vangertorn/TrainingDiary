package com.yankin.storage.room.converters

import com.yankin.models.TrainingDomain
import com.yankin.storage.room.entity.TrainingEntity

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

fun TrainingEntity.toModel() = TrainingDomain(
    id = id,
    date = date,
    muscleGroups = muscleGroups,
    comment = comment,
    weight = weight,
    position = position,
    deleted = deleted,
    selectedMuscleGroup = selectedMuscleGroup
)