package com.yankin.approach.api.usecases

import com.yankin.approach.api.models.ApproachDomain

interface GetApproachListUseCase {
    suspend fun invoke(exerciseId: Long): List<ApproachDomain>
}