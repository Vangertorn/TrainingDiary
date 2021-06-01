package com.example.trainingdiary.screen.exercise_list

import androidx.lifecycle.asLiveData
import com.example.trainingdiary.dao.ExerciseDao
import com.example.trainingdiary.dao.TrainingDao
import com.example.trainingdiary.repository.ExerciseRepository
import com.example.trainingdiary.repository.TrainingRepository
import com.example.trainingdiary.support.CoroutineViewModel
import kotlinx.coroutines.launch

class ExerciseListViewModel(
    private val exerciseRepository: ExerciseRepository,
    private val trainingRepository: TrainingRepository
) :
    CoroutineViewModel() {
    val exerciseLiveData = exerciseRepository.currentExerciseFlow.asLiveData()
    fun forgotIdTraining() {
        launch {
            trainingRepository.forgotIdTraining()
        }
    }

}