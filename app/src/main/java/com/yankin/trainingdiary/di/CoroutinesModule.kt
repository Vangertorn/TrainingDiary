package com.yankin.trainingdiary.di

import com.yankin.coroutine.CoroutineDispatchers
import com.yankin.coroutine.CoroutineDispatchersImpl
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object CoroutinesModule {

    @Provides
    @Reusable
    fun providesCoroutineDispatchers(): CoroutineDispatchers = CoroutineDispatchersImpl
}