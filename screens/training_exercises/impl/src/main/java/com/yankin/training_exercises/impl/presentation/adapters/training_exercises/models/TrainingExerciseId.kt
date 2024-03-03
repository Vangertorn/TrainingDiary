package com.yankin.training_exercises.impl.presentation.adapters.training_exercises.models

sealed interface TrainingExerciseId {
    val value: Long

    @JvmInline
    value class SingleExerciseId(override val value: Long): TrainingExerciseId

    @JvmInline
    value class SuperSetId(override val value: Long): TrainingExerciseId
}