package com.yankin.set.impl.domain.usecases

import com.yankin.set.api.models.SetDomain
import com.yankin.set.api.usecases.GetSetListUseCase
import com.yankin.set.impl.domain.repositories.SetRepository
import javax.inject.Inject

internal class GetSetListUseCaseImpl @Inject constructor(
    private val setRepository: SetRepository,
) : GetSetListUseCase {
    override suspend fun invoke(exerciseId: Long): List<SetDomain> {
        return setRepository.getSetList(exerciseId)
    }
}