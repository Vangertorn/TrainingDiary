package com.example.trainingdiary.screen.exercise_create

import com.example.trainingdiary.models.Exercise
import com.example.trainingdiary.repository.ExerciseRepository
import com.example.trainingdiary.support.CoroutineViewModel
import kotlinx.coroutines.launch

class ExerciseCreateViewModel(private val exerciseRepository: ExerciseRepository): CoroutineViewModel() {

     fun addNewExercise(exercise: Exercise){
         launch {
             exerciseRepository.saveExercise(exercise)
         }
     }
}