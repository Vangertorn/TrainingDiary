package com.yankin.approach.impl.domain.repositories

import com.yankin.approach.api.models.ApproachDomain
import kotlinx.coroutines.flow.Flow

internal interface ApproachRepository {

    val currentApproachStream: Flow<List<ApproachDomain>>

    suspend fun saveApproach(approach: ApproachDomain)

    suspend fun deleteApproach(approach: ApproachDomain)

    suspend fun getApproachList(exerciseId: Long): List<ApproachDomain>
}