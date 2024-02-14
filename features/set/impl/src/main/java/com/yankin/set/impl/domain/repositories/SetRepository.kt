package com.yankin.set.impl.domain.repositories

import com.yankin.set.api.models.SetDomain
import kotlinx.coroutines.flow.Flow

internal interface SetRepository {

    val currentSetStream: Flow<List<SetDomain>>

    suspend fun saveSet(set: SetDomain)

    suspend fun deleteSet(set: SetDomain)

    suspend fun getSetList(exerciseId: Long): List<SetDomain>
}