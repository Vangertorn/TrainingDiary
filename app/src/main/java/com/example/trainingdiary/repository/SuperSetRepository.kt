package com.example.trainingdiary.repository

import com.example.trainingdiary.dao.database.ExerciseDao
import com.example.trainingdiary.dao.database.SuperSetDao
import com.example.trainingdiary.datastore.AppSettings
import com.example.trainingdiary.models.Exercise
import com.example.trainingdiary.models.SuperSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.withContext

class SuperSetRepository(
    private val superSetDao: SuperSetDao,
    private val exerciseRepository: ExerciseRepository,
    appSettings: AppSettings,
    private val exerciseDao: ExerciseDao
) {
    @ExperimentalCoroutinesApi
    val currentExerciseInSuperSetFlow: Flow<MutableList<Exercise>> =
        appSettings.idSuperSetFlow().flatMapLatest {
            exerciseDao.getExercisesInfoByBySuperSetIdAndFlagsFlow(it, false)
        }

    suspend fun updateFlagVisibilitySuperSet(idSuperSet: Long) {
        withContext(Dispatchers.IO) {
            val superSet = superSetDao.getSuperSetById(idSuperSet)
            superSetDao.updateSuperSet(
                SuperSet(
                    id = superSet.id,
                    idTraining = superSet.idTraining,
                    deleted = superSet.deleted,
                    visibility = true,
                    position = superSet.position
                )
            )
        }
    }

    suspend fun deleteInvisibleSuperSet() {
        withContext(Dispatchers.IO) {
            superSetDao.deletedSuperSetByVisible()
        }
    }

    suspend fun saveSuperSet(
        superSet: SuperSet,
        exercise: Exercise,
        emptyExercise: Exercise
    ): Long {
        return withContext(Dispatchers.IO) {
            val id = superSetDao.insertSuperSet(superSet)
            exerciseRepository.saveExercise(
                Exercise(
                    id = emptyExercise.id,
                    name = emptyExercise.name,
                    idTraining = emptyExercise.idTraining,
                    position = emptyExercise.position,
                    comment = emptyExercise.comment,
                    deleted = emptyExercise.deleted,
                    idSet = id
                )
            )
            exerciseRepository.saveExercise(
                Exercise(
                    id = exercise.id,
                    name = exercise.name,
                    idTraining = exercise.idTraining,
                    position = exercise.position,
                    comment = exercise.comment,
                    deleted = exercise.deleted,
                    idSet = id
                )
            )

            return@withContext id
        }
    }
}