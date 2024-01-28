package com.yankin.api.usecases

import com.yankin.api.models.ThemeModel

interface GetThemeModelUseCase {
   fun invoke(): ThemeModel
}