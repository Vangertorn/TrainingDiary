package com.yankin.impl.data

import com.yankin.api.models.ThemeModel
import com.yankin.api.models.ThemeModel.Companion.fromCode
import com.yankin.impl.domain.repositories.ThemeRepository
import com.yankin.preferences.AppSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ThemeRepositoryImpl @Inject constructor(
    private val appSettings: AppSettings,
) : ThemeRepository {

    override val themeStream: Flow<ThemeModel> = appSettings.getIdThemeStream().map { intCode ->
        fromCode(intCode)
    }

    override suspend fun getTheme(): ThemeModel = fromCode(appSettings.getIdThemeStream().first())
}