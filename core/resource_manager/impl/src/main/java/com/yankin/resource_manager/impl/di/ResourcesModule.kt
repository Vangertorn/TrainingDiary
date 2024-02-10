package com.yankin.resource_manager.impl.di

import com.yankin.resource_manager.api.ResourceManager
import com.yankin.resource_manager.api.StringProvider
import com.yankin.resource_manager.impl.ResourceManagerImpl
import com.yankin.resource_manager.impl.StringProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface ResourcesModule {

    @Singleton
    @Binds
    fun bindsStringProvider(stringProviderImpl: StringProviderImpl): StringProvider

    @Singleton
    @Binds
    fun bindsResourceManager(resourceManagerImpl: ResourceManagerImpl): ResourceManager
}