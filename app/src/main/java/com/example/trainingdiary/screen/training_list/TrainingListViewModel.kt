package com.example.trainingdiary.screen.training_list

import androidx.lifecycle.asLiveData
import com.example.trainingdiary.datastore.AppSettings
import com.example.trainingdiary.models.MuscleGroup
import com.example.trainingdiary.models.Training
import com.example.trainingdiary.repository.MuscleGroupRepository
import com.example.trainingdiary.repository.TrainingRepository
import com.example.trainingdiary.support.CoroutineViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TrainingListViewModel(
    private val trainingRepository: TrainingRepository,
    private val appSettings: AppSettings,
) : CoroutineViewModel() {
    val trainingAscLiveData = trainingRepository.currentTrainingAscFlow.asLiveData()
    val trainingDescLiveData = trainingRepository.currentTrainingDescFlow.asLiveData()
    val switchOrderLiveData = appSettings.orderAddedFlow().asLiveData()

    fun deletedTrainingTrue(training: Training) {
        launch {
            trainingRepository.deletedTrainingTrue(training)
        }
    }

    fun getTrainingFromPosition(position: Int): Training? {
        return runBlocking {
            if (appSettings.orderAdded()) {
                return@runBlocking trainingAscLiveData.value?.get(position)
            } else {
                return@runBlocking trainingDescLiveData.value?.get(position)
            }
        }

    }

    fun deletedTrainingFalse(training: Training) {

        launch {

            trainingRepository.deletedTrainingFalse(training)

        }
    }

    fun rememberIdTraining(training: Training) {
        launch {
            appSettings.setIdTraining(training.id)
        }
    }

}