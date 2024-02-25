package com.yankin.approach_create.impl.di

import com.yankin.navigation.NavigationNode
import com.yankin.approach_create.impl.navigation.SetCreateNavigationNode
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
internal interface ApproachCreateNavigationModule {

    @IntoSet
    @Binds
    fun bindNavigationNode(setCreateNavigationNode: SetCreateNavigationNode): NavigationNode
}