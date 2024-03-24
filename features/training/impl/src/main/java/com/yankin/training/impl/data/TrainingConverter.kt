package com.yankin.training.impl.data

import com.yankin.date.Timestamp
import com.yankin.room.entity.TrainingEntity
import com.yankin.training.api.models.TrainingDomain
import com.yankin.training.impl.domain.models.TrainingModel

fun TrainingEntity.toModel() = TrainingModel(
    id = id,
    date = Timestamp.Milliseconds(date),
    comment = comment,
    personWeight = personWeight,
    selectedMuscleGroup = selectedMuscleGroup,
)

fun TrainingDomain.toModel() = TrainingModel(
    id = id,
    date = date,
    comment = comment,
    personWeight = personWeight,
    selectedMuscleGroup = selectedMuscleGroup.map { muscleGroupDomain ->
        muscleGroupDomain.id
    },
)