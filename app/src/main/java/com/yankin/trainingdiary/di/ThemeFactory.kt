package com.yankin.trainingdiary.di

import com.yankin.api.usecases.GetThemeModelUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ThemeFactory {
    fun getThemeModelUseCase(): GetThemeModelUseCase
}