package com.yankin.super_set.impl.data

import com.yankin.coroutine.CoroutineDispatchers
import com.yankin.super_set.api.models.SuperSetDomain
import com.yankin.super_set.impl.domain.repositories.SuperSetRepository
import com.yankin.preferences.AppSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class SuperSetRepositoryImpl @Inject constructor(
    private val superSetLocalDataSource: SuperSetLocalDataSource,
    private val coroutineDispatchers: CoroutineDispatchers,
) : SuperSetRepository {

    override fun getSuperSetByTrainingIdStream(trainingId: Long): Flow<List<SuperSetDomain>> =
        superSetLocalDataSource.getSuperSetListByTrainingIdStream(trainingId).map { superSetEntityList ->
            superSetEntityList.map { superSetEntity ->
                superSetEntity.toModel()
            }
        }

    override suspend fun saveSuperSet(superSet: SuperSetDomain): Long {
        return withContext(coroutineDispatchers.io) {
            superSetLocalDataSource.insertSuperSet(superSet = superSet.toEntity())
        }
    }

    override suspend fun deleteSuperSetTrue(superSetId: Long) {
        withContext(coroutineDispatchers.io) {
            superSetLocalDataSource.updateSuperSetDeleted(superSetId = superSetId, deleted = true)
        }
    }

    override suspend fun deleteSuperSetFalse(superSetId: Long) {
        withContext(coroutineDispatchers.io) {
            superSetLocalDataSource.updateSuperSetDeleted(superSetId = superSetId, deleted = false)
        }
    }

    override suspend fun switchSuperSetPosition(
        firstSuperSetId: Long,
        firstSuperSetPosition: Int,
        secondSuperSetId: Long,
        secondSuperSetPosition: Int
    ) {
        TODO("Not yet implemented")
    }
}
