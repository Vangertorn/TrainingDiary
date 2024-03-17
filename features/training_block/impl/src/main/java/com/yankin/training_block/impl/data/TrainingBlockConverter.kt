package com.yankin.training_block.impl.data

import com.yankin.room.entity.TrainingBlockEntity
import com.yankin.training_block.impl.domain.TrainingBlockModel

internal fun TrainingBlockEntity.toDomain() = TrainingBlockModel(
    id = id,
    trainingId = trainingId,
    position = position
)

internal fun TrainingBlockModel.toEntity() = TrainingBlockEntity(
    id = id,
    trainingId = trainingId,
    inDeleteQueue = false,
    position = position
)