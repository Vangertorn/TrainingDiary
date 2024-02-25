package com.yankin.set.impl.data

import com.yankin.set.api.models.SetDomain
import com.yankin.set.impl.domain.repositories.SetRepository
import com.yankin.coroutine.CoroutineDispatchers
import com.yankin.preferences.AppSettings
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class SetRepositoryImpl @Inject constructor(
    private val setLocalDataSource: SetLocalDataSource,
    private val coroutineDispatchers: CoroutineDispatchers,
    appSettings: AppSettings,
) : SetRepository {

    @ExperimentalCoroutinesApi
    override val currentSetStream: Flow<List<SetDomain>> =
        appSettings.idExerciseFlow().flatMapLatest { exerciseId ->
            setLocalDataSource.getSetListStream(exerciseId).map { setEntityList ->
                setEntityList?.map { setEntity -> setEntity.toDomain() } ?: emptyList()
            }
        }

    override fun getSetListStream(exerciseId: Long): Flow<List<SetDomain>> {
       return setLocalDataSource.getSetListStream(exerciseId).map { setEntityList ->
           setEntityList?.map { setEntity -> setEntity.toDomain() } ?: emptyList()
       }
    }

    override suspend fun saveSet(set: SetDomain) {
        withContext(coroutineDispatchers.io) {
            setLocalDataSource.insertSet(set.toEntity())
        }
    }

    override suspend fun deleteSetById(setId: Long) {
        withContext(coroutineDispatchers.io) {
            setLocalDataSource.deleteSetById(setId)
        }
    }

    override suspend fun getSetList(exerciseId: Long): List<SetDomain> {
        return withContext(coroutineDispatchers.io) {
            setLocalDataSource.getSetList(exerciseId)
                ?.map { setEntity -> setEntity.toDomain() } ?: emptyList()
        }
    }
}
