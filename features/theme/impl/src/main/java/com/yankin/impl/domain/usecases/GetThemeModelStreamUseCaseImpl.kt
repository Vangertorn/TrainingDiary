package com.yankin.impl.domain.usecases

import com.yankin.api.models.ThemeModel
import com.yankin.api.usecases.GetThemeModelStreamUseCase
import com.yankin.impl.domain.repositories.ThemeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetThemeModelStreamUseCaseImpl @Inject constructor(
    private val themeRepository: ThemeRepository,
) : GetThemeModelStreamUseCase {

    override fun invoke(): Flow<ThemeModel> = themeRepository.themeStream
}