package com.example.trainingdiary.screen.training_create

import com.example.trainingdiary.models.Training
import com.example.trainingdiary.repository.TrainingRepository
import com.example.trainingdiary.support.CoroutineViewModel
import kotlinx.coroutines.launch

class TrainingCreateViewModel(private val trainingRepository: TrainingRepository) :
    CoroutineViewModel() {

        fun addNewTraining(training: Training){
            launch {
                trainingRepository.saveTraining(training)
            }
        }

}