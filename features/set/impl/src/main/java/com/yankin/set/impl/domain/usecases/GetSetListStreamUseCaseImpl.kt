package com.yankin.set.impl.domain.usecases

import com.yankin.set.api.models.SetDomain
import com.yankin.set.api.usecases.GetSetListStreamUseCase
import com.yankin.set.impl.domain.repositories.SetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetSetListStreamUseCaseImpl  @Inject constructor(
    private val setRepository: SetRepository,
) : GetSetListStreamUseCase {
    override fun invoke(exerciseId: Long): Flow<List<SetDomain>> = setRepository.getSetListStream(exerciseId)
}