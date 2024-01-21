package com.yankin.training_create.impl.di

import com.yankin.training_create.api.navigation.TrainingCreateCommunicator
import com.yankin.training_create.impl.navigation.TrainingCreateCommunicatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
interface TrainingCreateModule {
    @Binds
    fun bindCommunicator(trainingCreateCommunicatorImpl: TrainingCreateCommunicatorImpl): TrainingCreateCommunicator
}