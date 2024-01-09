package com.yankin.super_set.impl.domain.usecases

import com.yankin.super_set.api.usecases.DeleteSuperSetTrueUseCase
import com.yankin.super_set.impl.domain.repositories.SuperSetRepository
import javax.inject.Inject

internal class DeleteSuperSetTrueUseCaseImpl @Inject constructor(
    private val superSetRepository: SuperSetRepository,
) : DeleteSuperSetTrueUseCase {

    override suspend fun invoke(superSetId: Long) {
        superSetRepository.deleteSuperSetTrue(superSetId)
    }
}