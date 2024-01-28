package com.yankin.impl.domain.repositories

import com.yankin.api.models.ThemeModel
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {

    suspend fun getTheme(): ThemeModel

    val themeStream: Flow<ThemeModel>
}