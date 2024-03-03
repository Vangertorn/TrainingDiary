package com.yankin.super_set.impl.domain.repositories

import com.yankin.super_set.api.models.SuperSetDomain
import kotlinx.coroutines.flow.Flow

internal interface SuperSetRepository {

    fun getSuperSetByTrainingIdStream(trainingId: Long): Flow<List<SuperSetDomain>>

    suspend fun saveSuperSet(superSet: SuperSetDomain): Long

    suspend fun deleteSuperSetTrue(superSetId: Long)

    suspend fun deleteSuperSetFalse(superSetId: Long)

    suspend fun switchSuperSetPosition(
        firstSuperSetId: Long,
        firstSuperSetPosition: Int,
        secondSuperSetId: Long,
        secondSuperSetPosition: Int
    )
}