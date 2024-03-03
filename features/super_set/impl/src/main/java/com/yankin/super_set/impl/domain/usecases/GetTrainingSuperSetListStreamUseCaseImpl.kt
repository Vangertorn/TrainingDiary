package com.yankin.super_set.impl.domain.usecases

import com.yankin.super_set.api.models.SuperSetDomain
import com.yankin.super_set.api.usecases.GetTrainingSuperSetListStreamUseCase
import com.yankin.super_set.impl.domain.repositories.SuperSetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetTrainingSuperSetListStreamUseCaseImpl @Inject constructor(
    private val superSetRepository: SuperSetRepository,
) : GetTrainingSuperSetListStreamUseCase {

    override fun invoke(trainingId: Long): Flow<List<SuperSetDomain>> {
        return superSetRepository.getSuperSetByTrainingIdStream(trainingId)
    }
}