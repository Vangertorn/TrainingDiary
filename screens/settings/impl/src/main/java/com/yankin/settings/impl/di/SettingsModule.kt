package com.yankin.settings.impl.di

import com.yankin.settings.api.navigation.SettingsCommunicator
import com.yankin.settings.impl.navigation.SettingsCommunicatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
interface SettingsModule {
    @Binds
    fun bindCommunicator(settingsCommunicatorImpl: SettingsCommunicatorImpl): SettingsCommunicator
}