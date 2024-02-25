package com.yankin.approach_create.impl.di

import com.yankin.approach_create.api.navigation.SetCreateCommunicator
import com.yankin.approach_create.impl.navigation.SetCreateCommunicatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
interface ApproachCreateModule {
    @Binds
    fun bindCommunicator(approachCreateCommunicatorImpl: SetCreateCommunicatorImpl): SetCreateCommunicator
}