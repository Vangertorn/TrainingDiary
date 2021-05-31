package com.example.trainingdiary.screen.exercise_list

import androidx.lifecycle.asLiveData
import com.example.trainingdiary.dao.ExerciseDao
import com.example.trainingdiary.support.CoroutineViewModel

class ExerciseListViewModel(private val exerciseDao: ExerciseDao) : CoroutineViewModel() {
    val exerciseLiveData = exerciseDao.getExerciseFlow().asLiveData()

}