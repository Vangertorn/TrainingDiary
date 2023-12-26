package com.yankin.approach.impl.data

import com.yankin.approach.api.models.ApproachDomain
import com.yankin.approach.impl.domain.repositories.ApproachRepository
import com.yankin.coroutine.CoroutineDispatchers
import com.yankin.preferences.AppSettings
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class ApproachRepositoryImpl @Inject constructor(
    private val approachLocalDataSource: ApproachLocalDataSource,
    private val coroutineDispatchers: CoroutineDispatchers,
    appSettings: AppSettings,
) : ApproachRepository {

    @ExperimentalCoroutinesApi
    override val currentApproachStream: Flow<List<ApproachDomain>> =
        appSettings.idExerciseFlow().flatMapLatest { exerciseId ->
            approachLocalDataSource.getApproachListStream(exerciseId).map { approachEntityList ->
                approachEntityList?.map { approachEntity -> approachEntity.toDomain() } ?: emptyList()
            }
        }

    override suspend fun saveApproach(approach: ApproachDomain) {
        withContext(coroutineDispatchers.io) {
            approachLocalDataSource.insertApproach(approach.toEntity())
        }
    }

    override suspend fun deleteApproach(approach: ApproachDomain) {
        withContext(coroutineDispatchers.io) {
            approachLocalDataSource.deleteApproach(approach.toEntity())
        }
    }

    override suspend fun getApproachList(exerciseId: Long): List<ApproachDomain> {
        return withContext(coroutineDispatchers.io) {
            approachLocalDataSource.getApproachList(exerciseId)
                ?.map { approachEntity -> approachEntity.toDomain() } ?: emptyList()
        }
    }
}
