package com.yankin.set.impl.domain.usecases

import com.yankin.set.api.models.SetDomain
import com.yankin.set.api.usecases.DeleteSetUseCase
import com.yankin.set.impl.domain.repositories.SetRepository
import javax.inject.Inject

internal class DeleteSetUseCaseImpl @Inject constructor(
    private val setRepository: SetRepository,
) : DeleteSetUseCase {

    override suspend fun invoke(set: SetDomain) {
        setRepository.deleteSet(set)
    }
}