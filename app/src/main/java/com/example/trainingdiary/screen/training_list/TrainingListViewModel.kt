package com.example.trainingdiary.screen.training_list

import androidx.lifecycle.asLiveData
import com.example.trainingdiary.datastore.AppSettings
import com.example.trainingdiary.models.Training
import com.example.trainingdiary.repository.TrainingRepository
import com.example.trainingdiary.support.CoroutineViewModel
import kotlinx.coroutines.launch

class TrainingListViewModel(private val trainingRepository: TrainingRepository, private val appSettings: AppSettings) : CoroutineViewModel() {
    val trainingLiveData = trainingRepository.currentTrainingFlow.asLiveData()

    fun deleteTraining(position: Int){
        launch {
            trainingRepository.deleteTraining(trainingLiveData.value?.get(position)!!)
        }
    }
    fun rememberIdTraining(training: Training){
        launch {
            appSettings.setIdTraining(training.id)
        }
    }
}