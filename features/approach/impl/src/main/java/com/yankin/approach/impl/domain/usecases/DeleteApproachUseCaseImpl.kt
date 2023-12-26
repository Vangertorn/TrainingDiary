package com.yankin.approach.impl.domain.usecases

import com.yankin.approach.api.models.ApproachDomain
import com.yankin.approach.api.usecases.DeleteApproachUseCase
import com.yankin.approach.impl.domain.repositories.ApproachRepository
import javax.inject.Inject

internal class DeleteApproachUseCaseImpl @Inject constructor(
    private val approachRepository: ApproachRepository,
) : DeleteApproachUseCase {

    override suspend fun invoke(approach: ApproachDomain) {
        approachRepository.deleteApproach(approach)
    }
}