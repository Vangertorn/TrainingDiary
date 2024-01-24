package com.yankin.exercise_create.impl.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.yankin.navigation.navigateToDestination
import com.yankin.exercise_create.api.navigation.ExerciseCreateCommunicator
import com.yankin.exercise_create.api.navigation.ExerciseCreateParams
import com.yankin.exercise_create.api.navigation.SuperSetCreateParams
import javax.inject.Inject

class ExerciseCreateCommunicatorImpl @Inject constructor(
    private val navController: NavController
): ExerciseCreateCommunicator {

    override fun navigateToExerciseCreate(params: ExerciseCreateParams) {
        navController.navigateToDestination(
            destinationRoute = ExerciseCreateNavigationNode.EXERCISE_CREATE_DIALOG_DESTINATION,
            args =  bundleOf(ExerciseCreateCommunicator.NAV_KEY to params.toParcelable()),
        )
    }

    override fun navigateToSuperSetCreate(params: SuperSetCreateParams) {
        navController.navigateToDestination(
            destinationRoute = ExerciseCreateNavigationNode.SUPER_SET_DIALOG_DIALOG_DESTINATION,
            args =  bundleOf(ExerciseCreateCommunicator.NAV_KEY to params.toParcelable()),
        )
    }
    private fun ExerciseCreateParams.toParcelable(): ExerciseCreateParcelableParams {
        return ExerciseCreateParcelableParams(trainingId = trainingId)
    }

    private fun SuperSetCreateParams.toParcelable(): SuperSetCreateParcelableParams {
        return SuperSetCreateParcelableParams(superSetId = superSetId)
    }
}