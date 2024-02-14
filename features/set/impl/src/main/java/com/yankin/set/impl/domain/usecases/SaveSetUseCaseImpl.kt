package com.yankin.set.impl.domain.usecases

import com.yankin.set.api.models.SetDomain
import com.yankin.set.api.usecases.SaveSetUseCase
import com.yankin.set.impl.domain.repositories.SetRepository
import javax.inject.Inject

internal class SaveSetUseCaseImpl @Inject constructor(
    private val setRepository: SetRepository,
) : SaveSetUseCase {
    override suspend fun invoke(set: SetDomain) {
        setRepository.saveSet(set)
    }
}