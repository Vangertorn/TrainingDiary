package com.yankin.common.themes

import android.content.Context
import com.yankin.api.AppThemeOwner
import com.yankin.api.models.ThemeModel.Companion.isNight

val Context.currentTheme: Int
    get() = (applicationContext as AppThemeOwner).theme.toThemeStyle()

val Context?.nightTheme: Boolean
    get() = (this?.applicationContext as? AppThemeOwner)?.theme.isNight()