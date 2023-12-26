package com.yankin.approach.api.usecases

import com.yankin.approach.api.models.ApproachDomain
import kotlinx.coroutines.flow.Flow

interface GetCurrentApproachStreamUseCase {
    fun invoke(): Flow<List<ApproachDomain>>
}