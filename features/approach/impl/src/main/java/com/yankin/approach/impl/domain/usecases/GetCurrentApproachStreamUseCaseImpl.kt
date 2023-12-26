package com.yankin.approach.impl.domain.usecases

import com.yankin.approach.api.models.ApproachDomain
import com.yankin.approach.api.usecases.GetCurrentApproachStreamUseCase
import com.yankin.approach.impl.domain.repositories.ApproachRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetCurrentApproachStreamUseCaseImpl @Inject constructor(
    private val approachRepository: ApproachRepository,
) : GetCurrentApproachStreamUseCase {
    override fun invoke(): Flow<List<ApproachDomain>> = approachRepository.currentApproachStream
}