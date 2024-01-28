package com.yankin.impl.domain.usecases

import com.yankin.api.models.ThemeModel
import com.yankin.api.usecases.GetThemeModelUseCase
import com.yankin.impl.domain.repositories.ThemeRepository
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class GetThemeModelUseCaseImpl @Inject constructor(
    private val themeRepository: ThemeRepository,
) : GetThemeModelUseCase {
    override fun invoke(): ThemeModel = runBlocking {
        themeRepository.getTheme()
    }
}