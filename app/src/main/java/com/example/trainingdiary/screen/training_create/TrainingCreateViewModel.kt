package com.example.trainingdiary.screen.training_create

import androidx.lifecycle.asLiveData
import com.example.trainingdiary.datastore.AppSettings
import com.example.trainingdiary.models.Training
import com.example.trainingdiary.repository.MuscleGroupRepository
import com.example.trainingdiary.repository.TrainingRepository
import com.example.trainingdiary.screen.training_list.TrainingListViewModel
import com.example.trainingdiary.support.CoroutineViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.StringBuilder

class TrainingCreateViewModel(
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