package com.yankin.super_set.impl.domain.usecases

import com.yankin.super_set.api.models.SuperSetDomain
import com.yankin.super_set.api.usecases.GetCurrentSuperSetListStreamUseCase
import com.yankin.super_set.impl.domain.repositories.SuperSetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetCurrentSuperSetListStreamUseCaseImpl @Inject constructor(
    private val superSetRepository: SuperSetRepository,
) : GetCurrentSuperSetListStreamUseCase {

    override fun invoke(): Flow<List<SuperSetDomain>> {
        return superSetRepository.currentSuperSetListStream
    }
}