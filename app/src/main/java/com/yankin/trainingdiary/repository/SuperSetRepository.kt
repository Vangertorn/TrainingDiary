package com.yankin.trainingdiary.repository

import com.yankin.storage.ExerciseStorage
import com.yankin.storage.SuperSetStorage
import com.yankin.preferences.AppSettings
import com.yankin.trainingdiary.models.Exercise
import com.yankin.trainingdiary.models.SuperSet
import com.yankin.trainingdiary.models.converters.toDomain
import com.yankin.trainingdiary.models.converters.toModel
import com.yankin.trainingdiary.models.info.ViewHolderTypes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class SuperSetRepository(
    private val superSetStorage: SuperSetStorage,
    private val exerciseRepository: ExerciseRepository,
    appSettings: AppSettings,
    private val exerciseStorage: ExerciseStorage
) {
    @ExperimentalCoroutinesApi
    val currentExerciseInSuperSetFlow: Flow<MutableList<Exercise>> =
        appSettings.idSuperSetFlow().flatMapLatest {
            exerciseStorage.getExercisesInfoByBySuperSetIdAndFlagsFlow(it, false).map {
                it.map {
                    it.toModel()
                }.toMutableList()
            }
        }
    val currentExerciseInfoInSuperSetFlow: Flow<List<ViewHolderTypes.ExerciseInfo>> =
        appSettings.idSuperSetFlow().flatMapLatest {
            exerciseStorage.getExercisesInfoBySuperSetIdAndFlagsFlow(it, false).map {
                it.map {
                    it.toModel()
                }
            }
        }

    val currentSuperSetDateFlow: Flow<List<ViewHolderTypes.SuperSetDate>> =
        appSettings.idTrainingFlow().flatMapLatest { idTraining ->
            superSetStorage.getSuperSetInfoByTrainingIdAndFlagsFlow(idTraining, false, true).map {
                it.map {
                    ViewHolderTypes.SuperSetDate(
                        superSet = it.superSetDomain.toModel(),
                        exercise = it.exerciseDomain!!.map {
                            exerciseStorage.getExerciseInfo(it.id).toModel()
                        }
                    )
                }
            }.flowOn(Dispatchers.IO)
        }

    suspend fun updateFlagVisibilitySuperSet(idSuperSet: Long) {
        withContext(Dispatchers.IO) {
            val superSet = superSetStorage.getSuperSetById(idSuperSet)
            superSetStorage.updateSuperSet(
                SuperSet(
                    id = superSet.id,
                    idTraining = superSet.idTraining,
                    deleted = superSet.deleted,
                    visibility = true,
                    position = superSet.position
                ).toDomain()
            )
        }
    }

    suspend fun deleteInvisibleSuperSet() {
        withContext(Dispatchers.IO) {
            superSetStorage.deletedSuperSetByVisible()
        }
    }

    suspend fun saveSuperSet(
        superSet: SuperSet,
        exercise: Exercise,
        emptyExercise: Exercise
    ): Long {
        return withContext(Dispatchers.IO) {
            val id = superSetStorage.insertSuperSet(superSet.toDomain())
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
            superSetStorage.updateSuperSet(
                SuperSet(
                    id = superSet.id,
                    idTraining = superSet.idTraining,
                    position = superSet.position,
                    deleted = true,
                    visibility = superSet.visibility
                ).toDomain()
            )
        }
    }

    suspend fun deletedSuperSetFalse(superSet: SuperSet) {
        withContext(Dispatchers.IO) {
            superSetStorage.updateSuperSet(
                SuperSet(
                    id = superSet.id,
                    idTraining = superSet.idTraining,
                    position = superSet.position,
                    deleted = false,
                    visibility = superSet.visibility
                ).toDomain()
            )
        }
    }

    suspend fun getListExerciseInfoById(idSuperSet: Long): List<ViewHolderTypes.ExerciseInfo> {
        return withContext(Dispatchers.IO) {
            return@withContext exerciseStorage.getListExerciseInfo(idSuperSet).map {
                it.toModel()
            }
        }
    }
}
