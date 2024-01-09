package com.yankin.super_set.impl.domain.usecases

import com.yankin.super_set.api.usecases.DeleteSuperSetFalseUseCase
import com.yankin.super_set.impl.domain.repositories.SuperSetRepository
import javax.inject.Inject

internal class DeleteSuperSetFalseUseCaseImpl @Inject constructor(
    private val superSetRepository: SuperSetRepository,
) : DeleteSuperSetFalseUseCase {

    override suspend fun invoke(superSetId: Long) {
        superSetRepository.deleteSuperSetFalse(superSetId)
    }
}