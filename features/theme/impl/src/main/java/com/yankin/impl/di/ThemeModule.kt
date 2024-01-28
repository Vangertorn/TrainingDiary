package com.yankin.impl.di

import com.yankin.api.usecases.GetThemeModelStreamUseCase
import com.yankin.api.usecases.GetThemeModelUseCase
import com.yankin.impl.data.ThemeRepositoryImpl
import com.yankin.impl.domain.repositories.ThemeRepository
import com.yankin.impl.domain.usecases.GetThemeModelStreamUseCaseImpl
import com.yankin.impl.domain.usecases.GetThemeModelUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface ThemeModule {

    @Binds
    fun bindsGetThemeModelUseCase(
        getThemeModelUseCaseImpl: GetThemeModelUseCaseImpl
    ): GetThemeModelUseCase

    @Binds
    fun bindsGetThemeModelStreamUseCase(
        getThemeModelStreamImpl: GetThemeModelStreamUseCaseImpl
    ): GetThemeModelStreamUseCase

    @Binds
    fun bindsThemeRepository(themeRepositoryImpl: ThemeRepositoryImpl): ThemeRepository
}