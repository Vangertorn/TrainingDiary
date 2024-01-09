package com.yankin.super_set.impl.domain.usecases

import com.yankin.super_set.api.usecases.DeleteSuperSetsUseCase
import com.yankin.super_set.impl.domain.repositories.SuperSetRepository
import javax.inject.Inject

internal class DeleteSuperSetsUseCaseImpl @Inject constructor(
    private val superSetRepository: SuperSetRepository,
) : DeleteSuperSetsUseCase {

    override suspend fun invoke() {
        superSetRepository.deleteSuperSets()
    }
}