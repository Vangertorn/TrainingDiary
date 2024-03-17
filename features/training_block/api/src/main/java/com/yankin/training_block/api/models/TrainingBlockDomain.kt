package com.yankin.training_block.api.models

import com.yankin.exercise.api.models.ExerciseDomain

sealed interface TrainingBlockDomain {
    val id: Long
    val trainingId: Long
    val position: Int

    data class SingleExercise(
        override val id: Long,
        override val trainingId: Long,
        override val position: Int,
        val exercise: ExerciseDomain,
    ) : TrainingBlockDomain

    data class SuperSet(
        override val id: Long,
        override val trainingId: Long,
        override val position: Int,
        val exerciseList: List<ExerciseDomain>
    ) : TrainingBlockDomain
}