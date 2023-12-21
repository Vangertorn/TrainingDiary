package com.yankin.trainingdiary.screen.training_create

import androidx.lifecycle.asLiveData
import com.yankin.muscle_groups.api.usecases.GetAllMuscleGroupListUseCase
import com.yankin.muscle_groups.api.usecases.GetCurrentMuscleGroupStreamUseCase
import com.yankin.training.api.usecases.SaveTrainingUseCase
import com.yankin.training.api.usecases.UpdateTrainingUseCase
import com.yankin.preferences.AppSettings
import com.yankin.trainingdiary.models.Training
import com.yankin.trainingdiary.models.converters.toDomain
import com.yankin.trainingdiary.screen.training_list.TrainingListViewModel
import com.yankin.trainingdiary.support.CoroutineViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class TrainingCreateViewModel @Inject constructor(
    private val getAllMuscleGroupListUseCase: GetAllMuscleGroupListUseCase,
    getCurrentMuscleGroupStreamUseCase: GetCurrentMuscleGroupStreamUseCase,
    private val saveTrainingUseCase: SaveTrainingUseCase,
    private val updateTrainingUseCase: UpdateTrainingUseCase,
    private val appSettings: AppSettings
) :
    CoroutineViewModel() {

    val muscleGroupLiveData = getCurrentMuscleGroupStreamUseCase.invoke().asLiveData()

    fun addNewTraining(training: Training) {
        launch {
            saveTrainingUseCase.invoke(training.toDomain())
        }
    }

    fun updateTraining(training: Training) {
        launch {
            updateTrainingUseCase.invoke(training.toDomain())
        }
    }

    fun addMuscleGroups(selectedPositions: List<Int>): String {
        val stringBuilder = StringBuilder()
        val listMuscleGroups = runBlocking {
            getAllMuscleGroupListUseCase.invoke()
        }
        for (index in listMuscleGroups.indices) {
            if (index in selectedPositions) {
                stringBuilder.append(listMuscleGroups[index].nameMuscleGroup)
                stringBuilder.append(", ")
            }
        }
        return stringBuilder.toString().removeSuffix(", ")
    }

    fun takeAwayTraining(training: Training) {
        launch {
            val numberTraining = appSettings.getNumberOfTrainingSessions()
            if (numberTraining < 100) {
                if (appSettings.getDateCreatedTicket().isNotEmpty()) {
                    if (TrainingListViewModel.monthFormatter.parse(training.date)!!
                        >= TrainingListViewModel.monthFormatter.parse(
                            appSettings.getDateCreatedTicket()
                        )
                    ) {
                        if (numberTraining == 0) {
                            appSettings.setNumberOfTrainingSessions(-1)
                            appSettings.setSubscriptionEndDate("")
                            appSettings.setDateCreatedTicket("")
                            appSettings.setDayLeft(-1)
                        } else {
                            appSettings.setNumberOfTrainingSessions(numberTraining - 1)
                        }
                    }
                }
            }
        }
    }
}
