package com.yankin.training_create.api.navigation

sealed interface TrainingCreateParams {
    data class EditTraining(
        val trainingId: Long,
        val date: String,
        val muscleGroups: String?,
        val comment: String?,
        val weight: String?,
        val position: Int,
        val deleted: Boolean,
        val selectedMuscleGroup: MutableList<Int>
    ) : TrainingCreateParams

    object CreateTraining : TrainingCreateParams
}