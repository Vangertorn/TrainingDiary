package com.yankin.trainingdiary.repository

import com.yankin.trainingdiary.dao.database.ExerciseDao
import com.yankin.trainingdiary.dao.database.SuperSetDao
import com.yankin.trainingdiary.datastore.AppSettings
import com.yankin.trainingdiary.models.Exercise
import com.yankin.trainingdiary.models.SuperSet
import com.yankin.trainingdiary.models.info.ViewHolderTypes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
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
    val currentExerciseInfoInSuperSetFlow: Flow<List<ViewHolderTypes.ExerciseInfo>> =
        appSettings.idSuperSetFlow().flatMapLatest {
            exerciseDao.getExercisesInfoBySuperSetIdAndFlagsFlow(it, false)
        }

    val currentSuperSetDateFlow: Flow<List<ViewHolderTypes.SuperSetDate>> =
        appSettings.idTrainingFlow().flatMapLatest { idTraining ->
            superSetDao.getSuperSetInfoByTrainingIdAndFlagsFlow(idTraining, false, true).map {
                it.map {
                    ViewHolderTypes.SuperSetDate(
                        superSet = it.superSet,
                        exercise = it.exercise!!.map {
                            exerciseDao.getExerciseInfo(it.id)
                        }
                    )
                }
            }.flowOn(Dispatchers.IO)
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

    suspend fun deletedSuperSetTrue(superSet: SuperSet) {
        withContext(Dispatchers.IO) {
            superSetDao.updateSuperSet(
                SuperSet(
                    id = superSet.id,
                    idTraining = superSet.idTraining,
                    position = superSet.position,
                    deleted = true,
                    visibility = superSet.visibility
                )
            )
        }
    }

    suspend fun deletedSuperSetFalse(superSet: SuperSet) {
        withContext(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                superSetDao.updateSuperSet(
                    SuperSet(
                        id = superSet.id,
                        idTraining = superSet.idTraining,
                        position = superSet.position,
                        deleted = false,
                        visibility = superSet.visibility
                    )
                )
            }
        }
    }

    suspend fun getListExerciseInfoById(idSuperSet: Long): List<ViewHolderTypes.ExerciseInfo> {
        return withContext(Dispatchers.IO) {
            return@withContext exerciseDao.getListExerciseInfo(idSuperSet)
        }
    }
}
