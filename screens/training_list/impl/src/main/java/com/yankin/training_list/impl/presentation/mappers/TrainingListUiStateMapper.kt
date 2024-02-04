package com.yankin.training_list.impl.presentation.mappers

import com.yankin.date.DateFormatter
import com.yankin.date.DateFormatter.toDateStringOrEmpty
import com.yankin.training_list.impl.presentation.models.TrainingListStateModel
import com.yankin.training_list.impl.presentation.models.TrainingListUiState

internal fun TrainingListStateModel.toTrainingListUiState(): TrainingListUiState {

    return TrainingListUiState(
        daysLeft = membership?.let { membership ->
            membership.endDate?.let { endDate ->
                val timeLeft = endDate - membership.startDate
                if (timeLeft.value > 0L) {
                    timeLeft.toDateStringOrEmpty(dateFormat = DateFormatter.D)
                } else {
                    "-"
                }

            } ?: "ထ"
        } ?: "",
        trainingLeft = membership?.let { membership ->
            membership.trainingCount?.let { trainingCount ->
                val trainingLeft = trainingCount - membership.accountedTrainingIdList.size
                if (trainingLeft > 0) {
                    trainingLeft.toString()
                } else {
                    "-"
                }
            } ?: "ထ"
        } ?: ""
    )
}