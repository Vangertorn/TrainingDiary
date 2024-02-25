package com.yankin.set.impl.domain.usecases

import com.yankin.set.api.usecases.DeleteSetByIdUseCase
import com.yankin.set.impl.domain.repositories.SetRepository
import javax.inject.Inject

internal class DeleteSetByIdByIdUseCaseImpl @Inject constructor(
    private val setRepository: SetRepository,
) : DeleteSetByIdUseCase {

    override suspend fun invoke(setId: Long) {
        setRepository.deleteSetById(setId)
    }
}