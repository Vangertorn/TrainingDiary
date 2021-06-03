package com.example.trainingdiary.screen.training_create

import androidx.lifecycle.asLiveData
import com.example.trainingdiary.models.Training
import com.example.trainingdiary.repository.MuscleGroupRepository
import com.example.trainingdiary.repository.TrainingRepository
import com.example.trainingdiary.support.CoroutineViewModel
import kotlinx.coroutines.launch

class TrainingCreateViewModel(
    private val trainingRepository: TrainingRepository,
    muscleGroupRepository: MuscleGroupRepository
) :
    CoroutineViewModel() {

    fun addNewTraining(training: Training) {
        launch {
            trainingRepository.saveTraining(training)
        }
    }

    val muscleGroupLiveData = muscleGroupRepository.currentMuscleGroupFlow.asLiveData()

}