package com.yankin.super_set.impl.domain.usecases

import com.yankin.super_set.api.models.SuperSetDomain
import com.yankin.super_set.api.usecases.SaveSuperSetUseCase
import com.yankin.super_set.impl.domain.repositories.SuperSetRepository
import javax.inject.Inject

internal class SaveSuperSetUseCaseImpl @Inject constructor(
    private val superSetRepository: SuperSetRepository,
) : SaveSuperSetUseCase {
    override suspend fun invoke(superSet: SuperSetDomain): Long {
        return superSetRepository.saveSuperSet(superSet)
    }
}