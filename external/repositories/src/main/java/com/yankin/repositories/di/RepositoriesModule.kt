package com.yankin.repositories.di

import com.yankin.domain.api.repositories.MuscleGroupRepository
import com.yankin.repositories.impl.MuscleGroupRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoriesModule {

    @Binds
    fun bindsMuscleGroupRepository(muscleGroupRepositoryImpl: MuscleGroupRepositoryImpl): MuscleGroupRepository
}