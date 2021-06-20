package com.example.trainingdiary.screen.exercise_list

import androidx.lifecycle.asLiveData
import com.example.trainingdiary.datastore.AppSettings
import com.example.trainingdiary.models.Exercise
import com.example.trainingdiary.repository.ExerciseRepository
import com.example.trainingdiary.repository.TrainingRepository
import com.example.trainingdiary.support.CoroutineViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class ExerciseListViewModel(
    private val exerciseRepository: ExerciseRepository,
    private val trainingRepository: TrainingRepository,
    private val appSettings: AppSettings
) :
    CoroutineViewModel() {


    @ExperimentalCoroutinesApi
    val exerciseLiveData = exerciseRepository.currentExerciseFlow.asLiveData()

    @ExperimentalCoroutinesApi
    fun getExerciseFromPosition(position: Int): Exercise {
        return exerciseLiveData.value?.get(position)!!.exercise
    }

    fun deletedExerciseTrue(exercise: Exercise) {
        launch {
            exerciseRepository.deletedExerciseTrue(exercise)
        }
    }

    fun deletedExerciseFalse(exercise: Exercise) {
        launch {
            exerciseRepository.deletedExerciseFalse(exercise)
        }
    }

    fun forgotIdTraining() {
        launch {
            trainingRepository.forgotIdTraining()
        }
    }



    fun rememberIdExercise(exercise: Exercise) {
        launch {
            appSettings.setIdExercise(exercise.id)
        }
    }

    fun switchExercisePosition(exercise1: Exercise, exercise2: Exercise) {
        launch {
            exerciseRepository.switchExercisePosition(exercise1, exercise2)
        }
    }


}