package com.yankin.super_set.api.usecases

import com.yankin.super_set.api.models.SuperSetDomain

interface SaveSuperSetUseCase {
    suspend fun invoke(superSet: SuperSetDomain): Long
}