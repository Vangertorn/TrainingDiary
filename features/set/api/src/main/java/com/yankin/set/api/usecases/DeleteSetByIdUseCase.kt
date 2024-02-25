package com.yankin.set.api.usecases

interface DeleteSetByIdUseCase {

    suspend fun invoke(setId: Long)
}