package com.yankin.workout_routines.impl.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.yankin.workout_routines.api.navigation.WorkoutRoutinesCommunicator
import com.yankin.workout_routines.api.navigation.WorkoutRoutinesParams
import com.yankin.navigation.navigateToDestination
import javax.inject.Inject

class WorkoutRoutinesCommunicatorImpl @Inject constructor(
    private val navController: NavController
) : WorkoutRoutinesCommunicator {

    override fun navigateTo(params: WorkoutRoutinesParams) {
        navController.navigateToDestination(
            destinationRoute = WorkoutRoutinesNavigationNode.ROUTE,
            args = bundleOf(WorkoutRoutinesCommunicator.NAV_KEY to params.toParcelable()),
        )
    }

    private fun WorkoutRoutinesParams.toParcelable(): WorkoutRoutinesParcelableParams {
        return WorkoutRoutinesParcelableParams(
            trainingId = trainingId,
        )
    }
}