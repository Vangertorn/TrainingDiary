package com.yankin.set.api.usecases

import com.yankin.set.api.models.SetDomain

interface GetSetListUseCase {
    suspend fun invoke(exerciseId: Long): List<SetDomain>
}