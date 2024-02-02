package com.yankin.training_create.api.navigation

sealed interface TrainingCreateParams {
    data class EditTraining(
        val trainingId: Long,
    ) : TrainingCreateParams

    object CreateTraining : TrainingCreateParams
}