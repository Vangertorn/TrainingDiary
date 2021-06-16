package com.example.trainingdiary

import com.example.trainingdiary.repository.ExerciseRepository
import com.example.trainingdiary.repository.MuscleGroupRepository
import com.example.trainingdiary.repository.TrainingRepository
import com.example.trainingdiary.support.CoroutineViewModel
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val trainingRepository: TrainingRepository,
    private val exerciseRepository: ExerciseRepository,
    private val muscleGroupRepository: MuscleGroupRepository
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
    init {
        launch {
            muscleGroupRepository.saveDefaultValues(
            )
        }

    }
}