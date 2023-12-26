package com.yankin.approach.impl.domain.usecases

import com.yankin.approach.api.models.ApproachDomain
import com.yankin.approach.api.usecases.GetApproachListUseCase
import com.yankin.approach.impl.domain.repositories.ApproachRepository
import javax.inject.Inject

internal class GetApproachListUseCaseImpl @Inject constructor(
    private val approachRepository: ApproachRepository,
) : GetApproachListUseCase {
    override suspend fun invoke(exerciseId: Long): List<ApproachDomain> {
        return approachRepository.getApproachList(exerciseId)
    }
}