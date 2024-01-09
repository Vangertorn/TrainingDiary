package com.yankin.super_set.api.usecases


interface UpdateSuperSetVisibleUseCase {
    suspend fun invoke(superSetId: Long)
}