package com.yankin.approach.impl.domain.usecases

import com.yankin.approach.api.models.ApproachDomain
import com.yankin.approach.api.usecases.SaveApproachUseCase
import com.yankin.approach.impl.domain.repositories.ApproachRepository
import javax.inject.Inject

internal class SaveApproachUseCaseImpl @Inject constructor(
    private val approachRepository: ApproachRepository,
) : SaveApproachUseCase {
    override suspend fun invoke(approach: ApproachDomain) {
        approachRepository.saveApproach(approach)
    }
}