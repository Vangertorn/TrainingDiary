package com.yankin.training_list.impl.presentation.mappers

import com.yankin.training_list.impl.presentation.models.TrainingListStateModel
import com.yankin.training_list.impl.presentation.models.TrainingListUiState
import java.util.Calendar
import java.util.concurrent.TimeUnit

internal fun TrainingListStateModel.toTrainingListUiState(): TrainingListUiState {

    return TrainingListUiState(
        daysLeft = membership?.let { membership ->
            membership.endDate?.let {
                val timeLeft = it.value - Calendar.getInstance().time.time
                if (timeLeft > 0) {
                    TimeUnit.MILLISECONDS.toDays(timeLeft).toString()
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