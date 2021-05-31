package com.example.trainingdiary.repository

import com.example.trainingdiary.dao.ExerciseDao
import com.example.trainingdiary.models.Exercise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ExerciseRepository(private val exerciseDao: ExerciseDao) {

    val currentExerciseFlow: Flow<List<Exercise>> = exerciseDao.getExerciseFlow().map {
        it ?: listOf()
    }
    suspend fun saveExercise(exercise: Exercise){
        withContext(Dispatchers.IO){
            exerciseDao.saveExercise(exercise)
        }
    }
    suspend fun deleteExercise(exercise: Exercise){
        withContext(Dispatchers.IO){
            exerciseDao.deleteExercise((exercise))
        }
    }
}