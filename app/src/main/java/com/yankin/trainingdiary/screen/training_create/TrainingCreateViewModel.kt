package com.yankin.trainingdiary.screen.training_create

import androidx.lifecycle.asLiveData
import com.yankin.trainingdiary.datastore.AppSettings
import com.yankin.trainingdiary.models.Training
import com.yankin.trainingdiary.repository.MuscleGroupRepository
import com.yankin.trainingdiary.repository.TrainingRepository
import com.yankin.trainingdiary.screen.training_list.TrainingListViewModel
import com.yankin.trainingdiary.support.CoroutineViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class TrainingCreateViewModel @Inject constructor(
    private val trainingRepository: TrainingRepository,
    private val muscleGroupRepository: MuscleGroupRepository,
    private val appSettings: AppSettings
) :
    CoroutineViewModel() {

    val muscleGroupLiveData = muscleGroupRepository.currentMuscleGroupFlow.asLiveData()

    fun addNewTraining(training: Training) {
        launch {
            trainingRepository.saveTraining(training)
        }
    }

    fun updateTraining(training: Training) {
        launch {
            trainingRepository.updateTraining(training)
        }
    }

    fun addMuscleGroups(selectedPositions: List<Int>): String {
        val stringBuilder = StringBuilder()
        val listMuscleGroups = runBlocking {
            muscleGroupRepository.getMuscleGroups()
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