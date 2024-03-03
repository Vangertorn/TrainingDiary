package com.yankin.training_exercises.impl.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.yankin.training_exercises.api.navigation.TrainingExercisesCommunicator
import com.yankin.training_exercises.api.navigation.TrainingExercisesParams
import com.yankin.navigation.navigateToDestination
import javax.inject.Inject

class TrainingExercisesCommunicatorImpl @Inject constructor(
    private val navController: NavController
) : TrainingExercisesCommunicator {

    override fun navigateTo(params: TrainingExercisesParams) {
        navController.navigateToDestination(
            destinationRoute = TrainingExercisesNavigationNode.ROUTE,
            args = bundleOf(TrainingExercisesCommunicator.NAV_KEY to params.toParcelable()),
        )
    }

    private fun TrainingExercisesParams.toParcelable(): TrainingExercisesParcelableParams {
        return TrainingExercisesParcelableParams(
            trainingId = trainingId,
        )
    }
}