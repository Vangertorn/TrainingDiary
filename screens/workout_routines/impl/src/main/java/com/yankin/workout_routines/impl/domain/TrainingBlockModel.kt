package com.yankin.workout_routines.impl.domain

import com.yankin.training_block.api.models.TrainingBlockDomain

internal sealed interface TrainingBlockModel {
    val trainingBlockDomain: TrainingBlockDomain

    data class SingleExercise(
        override val trainingBlockDomain: TrainingBlockDomain,
        val exercise: ExerciseModel,
    ) : TrainingBlockModel

    data class SuperSet(
        override val trainingBlockDomain: TrainingBlockDomain,
        val exerciseList: List<ExerciseModel>
    ) : TrainingBlockModel
}