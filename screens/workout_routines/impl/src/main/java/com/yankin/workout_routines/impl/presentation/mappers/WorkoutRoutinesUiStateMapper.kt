package com.yankin.workout_routines.impl.presentation.mappers

import com.yankin.date.DateFormatter
import com.yankin.date.DateFormatter.toDateStringOrEmpty
import com.yankin.muscle_groups.api.models.MuscleGroupDomain
import com.yankin.resource_manager.api.ResourceManager
import com.yankin.workout_routines.impl.domain.TrainingBlockModel
import com.yankin.workout_routines.impl.presentation.models.WorkoutRoutinesStateModel
import com.yankin.workout_routines.impl.presentation.models.WorkoutRoutinesUiState

internal fun WorkoutRoutinesStateModel.toWorkoutRoutinesUiState(
    resourceManager: ResourceManager
): WorkoutRoutinesUiState {

    return WorkoutRoutinesUiState(
        trainingDate = trainingDomain?.date?.toDateStringOrEmpty(dateFormat = DateFormatter.DD_POINT_MM_POINT_YY) ?: "",
        trainingComment = trainingDomain?.comment ?: "",
        exercises = trainingBlockList.map { trainingBlockModel ->
            when (trainingBlockModel) {
                is TrainingBlockModel.SingleExercise -> trainingBlockModel.toSingleExerciseUiModel(resourceManager)
                is TrainingBlockModel.SuperSet -> trainingBlockModel.toSuperSetUiModel(resourceManager)
            }
        },
        trainingMuscleGroups = trainingDomain?.selectedMuscleGroup?.getMuscleGroupsString() ?: "",
        trainingWeight = trainingDomain?.personWeight?.toString() ?: "",
    )
}

private fun List<MuscleGroupDomain>.getMuscleGroupsString(): String {
    val stringBuilder = StringBuilder()
    forEach { muscleGroup ->
        stringBuilder.append(muscleGroup.nameMuscleGroup)
        stringBuilder.append(", ")
    }
    return stringBuilder.toString().removeSuffix(", ")
}