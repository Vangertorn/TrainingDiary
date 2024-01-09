package com.yankin.super_set.api.usecases

interface DeleteSuperSetFalseUseCase {

    suspend fun invoke(superSetId: Long)
}