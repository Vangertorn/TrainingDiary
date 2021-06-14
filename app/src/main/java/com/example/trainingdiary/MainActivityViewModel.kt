package com.example.trainingdiary

import com.example.trainingdiary.repository.ExerciseRepository
import com.example.trainingdiary.repository.TrainingRepository
import com.example.trainingdiary.support.CoroutineViewModel
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val trainingRepository: TrainingRepository,
    private val exerciseRepository: ExerciseRepository
) : CoroutineViewModel() {

    fun deletedTrainings() {
        launch {
            trainingRepository.deletedTrainingsByFlags()
        }
    }
    fun deletedExercises(){
        launch {
            exerciseRepository.deleteExercises()
        }
    }
}