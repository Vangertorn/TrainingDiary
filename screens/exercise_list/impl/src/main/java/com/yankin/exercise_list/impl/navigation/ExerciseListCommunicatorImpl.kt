package com.yankin.exercise_list.impl.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.yankin.exercise_list.api.navigation.ExerciseListCommunicator
import com.yankin.exercise_list.api.navigation.ExerciseListParams
import com.yankin.navigation.navigateToDestination
import javax.inject.Inject

class ExerciseListCommunicatorImpl @Inject constructor(
    private val navController: NavController
) : ExerciseListCommunicator {

    override fun navigateTo(params: ExerciseListParams) {
        navController.navigateToDestination(
            destinationRoute = ExerciseListNavigationNode.ROUTE,
            args = bundleOf(ExerciseListCommunicator.NAV_KEY to params.toParcelable()),
        )
    }

    private fun ExerciseListParams.toParcelable(): ExerciseListParcelableParams {
        return ExerciseListParcelableParams(
            trainingId = trainingId,
            date = date,
            muscleGroups = muscleGroups,
            comment = comment,
            weight = weight,
            position = position,
            deleted = deleted,
            selectedMuscleGroup = selectedMuscleGroup,
        )
    }
}