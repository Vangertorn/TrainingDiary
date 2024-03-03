package com.yankin.super_set.api.usecases

import com.yankin.super_set.api.models.SuperSetDomain
import kotlinx.coroutines.flow.Flow

interface GetTrainingSuperSetListStreamUseCase {

    fun invoke(trainingId: Long): Flow<List<SuperSetDomain>>
}