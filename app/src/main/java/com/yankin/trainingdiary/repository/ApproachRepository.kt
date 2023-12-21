package com.yankin.trainingdiary.repository

import com.yankin.storage.ApproachStorage
import com.yankin.storage.ExerciseStorage
import com.yankin.preferences.AppSettings
import com.yankin.trainingdiary.models.Approach
import com.yankin.trainingdiary.models.converters.toDomain
import com.yankin.trainingdiary.models.converters.toModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ApproachRepository(
    private val approachStorage: ApproachStorage,
    private val exerciseStorage: ExerciseStorage,
    appSettings: AppSettings
) {

    @ExperimentalCoroutinesApi
    val currentApproachFlow: Flow<List<Approach>> =
        appSettings.idExerciseFlow().flatMapLatest { idExercise ->
            exerciseStorage.getExerciseInfoFlow(idExercise).map {
                it?.approachDomains?.map {
                    it.toModel()
                } ?: emptyList()
            }
        }

    suspend fun saveApproach(approach: Approach) {
        withContext(Dispatchers.IO) {
            approachStorage.insertApproach(approach.toDomain())
        }
    }

    suspend fun deleteApproach(approach: Approach) {
        withContext(Dispatchers.IO) {
            approachStorage.deleteApproach(approach.toDomain())
        }
    }
}
