package com.yankin.set.api.usecases

import com.yankin.set.api.models.SetDomain

interface SaveSetUseCase {
    suspend fun invoke(set: SetDomain)
}