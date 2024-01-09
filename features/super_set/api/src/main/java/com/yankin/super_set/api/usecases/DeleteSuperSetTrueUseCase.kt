package com.yankin.super_set.api.usecases

interface DeleteSuperSetTrueUseCase {

    suspend fun invoke(superSetId: Long)
}