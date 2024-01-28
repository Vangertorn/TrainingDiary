package com.yankin.common.themes

import androidx.annotation.StyleRes
import com.yankin.api.models.ThemeModel
import com.yankin.common.resource_import.CommonRStyle

@StyleRes
fun ThemeModel.toThemeStyle(): Int =
    when (this) {
        ThemeModel.Dark -> CommonRStyle.Theme_TrainingDiary_Dark
        ThemeModel.Light -> CommonRStyle.Theme_TrainingDiary_Light
        ThemeModel.Night -> CommonRStyle.Theme_TrainingDiary_Night
    }