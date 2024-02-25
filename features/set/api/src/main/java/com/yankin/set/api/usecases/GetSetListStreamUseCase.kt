package com.yankin.set.api.usecases

import com.yankin.set.api.models.SetDomain
import kotlinx.coroutines.flow.Flow

interface GetSetListStreamUseCase {
    fun invoke(exerciseId: Long): Flow<List<SetDomain>>
}