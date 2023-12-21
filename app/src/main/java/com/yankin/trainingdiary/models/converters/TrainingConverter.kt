package com.yankin.trainingdiary.models.converters

import com.yankin.training.api.models.TrainingDomain
import com.yankin.trainingdiary.models.Training

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