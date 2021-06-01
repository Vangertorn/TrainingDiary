package com.example.trainingdiary.repository

import com.example.trainingdiary.dao.database.ApproachDao
import com.example.trainingdiary.dao.database.ExerciseDao
import com.example.trainingdiary.datastore.AppSettings
import com.example.trainingdiary.models.Approach
import com.example.trainingdiary.models.Exercise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ApproachRepository(
    private val approachDao: ApproachDao,
    private val exerciseDao: ExerciseDao,
    private val appSettings: AppSettings
) {

    val currentApproachFlow: Flow<List<Approach>> =
        appSettings.idExerciseFlow().flatMapLatest { idExercise ->
            exerciseDao.getExerciseInfoFlow(idExercise).map {
                it?.approaches ?: emptyList()
            }
        }


    suspend fun saveApproach(approach: Approach) {
        withContext(Dispatchers.IO) {
            approachDao.insertApproach(approach)
        }
    }

    suspend fun deleteApproach(approach: Approach) {
        withContext(Dispatchers.IO) {
            approachDao.deleteApproach(approach)
        }
    }


}