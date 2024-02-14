package com.yankin.set.api.usecases

import com.yankin.set.api.models.SetDomain

interface DeleteSetUseCase {

    suspend fun invoke(set: SetDomain)
}