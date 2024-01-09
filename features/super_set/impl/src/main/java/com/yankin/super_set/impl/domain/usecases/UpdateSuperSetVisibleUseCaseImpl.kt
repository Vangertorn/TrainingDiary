package com.yankin.super_set.impl.domain.usecases

import com.yankin.super_set.api.usecases.UpdateSuperSetVisibleUseCase
import com.yankin.super_set.impl.domain.repositories.SuperSetRepository
import javax.inject.Inject

internal class UpdateSuperSetVisibleUseCaseImpl @Inject constructor(
    private val superSetRepository: SuperSetRepository,
) : UpdateSuperSetVisibleUseCase {
    override suspend fun invoke(superSetId: Long) {
        superSetRepository.updateSuperSetVisible(superSetId)
    }
}