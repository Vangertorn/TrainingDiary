package com.yankin.approach.api.usecases

import com.yankin.approach.api.models.ApproachDomain

interface SaveApproachUseCase {
    suspend fun invoke(approach: ApproachDomain)
}