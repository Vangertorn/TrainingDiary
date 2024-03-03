package com.yankin.training_exercises.impl.di

import com.yankin.training_exercises.api.navigation.TrainingExercisesCommunicator
import com.yankin.training_exercises.impl.navigation.TrainingExercisesCommunicatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
interface TrainingExercisesModule {
    @Binds
    fun bindCommunicator(
        trainingExercisesCommunicatorImpl: TrainingExercisesCommunicatorImpl
    ): TrainingExercisesCommunicator
}