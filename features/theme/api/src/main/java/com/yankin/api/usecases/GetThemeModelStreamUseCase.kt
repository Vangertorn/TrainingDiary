package com.yankin.api.usecases

import com.yankin.api.models.ThemeModel
import kotlinx.coroutines.flow.Flow

interface GetThemeModelStreamUseCase {
    fun invoke(): Flow<ThemeModel>
}