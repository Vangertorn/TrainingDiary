package com.yankin.workout_routines.impl.presentation.adapters.training_exercises.models

sealed interface TrainingBlockId {
    val value: Long

    @JvmInline
    value class SingleBlockId(override val value: Long): TrainingBlockId

    @JvmInline
    value class SuperSetId(override val value: Long): TrainingBlockId
}