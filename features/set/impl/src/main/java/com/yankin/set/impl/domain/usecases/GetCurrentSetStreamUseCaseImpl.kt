package com.yankin.set.impl.domain.usecases

import com.yankin.set.api.models.SetDomain
import com.yankin.set.api.usecases.GetCurrentSetStreamUseCase
import com.yankin.set.impl.domain.repositories.SetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetCurrentSetStreamUseCaseImpl @Inject constructor(
    private val setRepository: SetRepository,
) : GetCurrentSetStreamUseCase {
    override fun invoke(): Flow<List<SetDomain>> = setRepository.currentSetStream
}