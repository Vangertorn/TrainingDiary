package com.yankin.set.impl.domain.repositories

import com.yankin.set.api.models.SetDomain
import kotlinx.coroutines.flow.Flow

internal interface SetRepository {

    fun getSetListStream(exerciseId: Long): Flow<List<SetDomain>>

    suspend fun saveSet(set: SetDomain)

    suspend fun deleteSetById(setId: Long)

    suspend fun getSetList(exerciseId: Long): List<SetDomain>
}