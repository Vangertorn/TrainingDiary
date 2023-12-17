package com.yankin.trainingdiary.di

import com.yankin.coroutine.CoroutineDispatchers
import com.yankin.coroutine.CoroutineDispatchersImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface CoroutinesModule {

    @Binds
    @Reusable
    fun bindCoroutineDispatchers(
        coroutineDispatchersImpl: CoroutineDispatchersImpl
    ): CoroutineDispatchers
}