package com.yankin.approach.api.usecases

import com.yankin.approach.api.models.ApproachDomain

interface DeleteApproachUseCase {

    suspend fun invoke(approach: ApproachDomain)
}