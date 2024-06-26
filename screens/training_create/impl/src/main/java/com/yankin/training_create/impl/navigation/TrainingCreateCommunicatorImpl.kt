package com.yankin.training_create.impl.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.yankin.navigation.navigateToDestination
import com.yankin.training_create.api.navigation.TrainingCreateCommunicator
import com.yankin.training_create.api.navigation.TrainingCreateParams
import com.yankin.training_create.impl.presentation.Training
import javax.inject.Inject

class TrainingCreateCommunicatorImpl @Inject constructor(
    private val navController: NavController
) : TrainingCreateCommunicator {

    override fun navigateTo(params: TrainingCreateParams) {
        navController.navigateToDestination(
            destinationRoute = TrainingCreateNavigationNode.ROUTE,
            args = bundleOf(TrainingCreateCommunicator.NAV_KEY to params.toParcelable())

        )
    }

    private fun TrainingCreateParams.toParcelable(): TrainingCreateParcelableParams {
        return when (this) {
            TrainingCreateParams.CreateTraining -> TrainingCreateParcelableParams(null)
            is TrainingCreateParams.EditTraining ->
                TrainingCreateParcelableParams(
                    Training(
                        id = trainingId,
                        date = date,
                        muscleGroups = muscleGroups,
                        comment = comment,
                        weight = weight,
                        position = position,
                        deleted = deleted,
                        selectedMuscleGroup = selectedMuscleGroup,
                    )

                )
        }
    }
}