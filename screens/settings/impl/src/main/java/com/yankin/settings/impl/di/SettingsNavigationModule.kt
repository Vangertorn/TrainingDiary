package com.yankin.settings.impl.di

import com.yankin.navigation.NavigationNode
import com.yankin.settings.impl.navigation.SettingsNavigationNode
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
internal interface SettingsNavigationModule {

    @IntoSet
    @Binds
    fun bindNavigationNode(settingsNavigationNode: SettingsNavigationNode): NavigationNode
}