package com.yankin.super_set.api.usecases

import com.yankin.super_set.api.models.SuperSetDomain
import kotlinx.coroutines.flow.Flow

interface GetCurrentSuperSetListStreamUseCase {

    fun invoke(): Flow<List<SuperSetDomain>>
}