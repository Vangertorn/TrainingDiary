package com.yankin.training_create.impl.presentation

import com.yankin.training.api.models.TrainingDomain

fun Training.toDomain() = TrainingDomain(
    id = id,
    date = date,
    muscleGroups = muscleGroups,
    comment = comment,
    weight = weight,
    position = position,
    deleted = deleted,
    selectedMuscleGroup = selectedMuscleGroup
)

fun TrainingDomain.toModel() = Training(
    id = id,
    date = date,
    muscleGroups = muscleGroups,
    comment = comment,
    weight = weight,
    position = position,
    deleted = deleted,
    selectedMuscleGroup = selectedMuscleGroup
)