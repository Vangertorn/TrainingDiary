package com.yankin.exercise_create.impl.di

import com.yankin.exercise_create.api.navigation.ExerciseCreateCommunicator
import com.yankin.exercise_create.impl.navigation.ExerciseCreateCommunicatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
interface ExerciseCreateModule {
    @Binds
    fun bindCommunicator(exerciseCreateCommunicatorImpl: ExerciseCreateCommunicatorImpl): ExerciseCreateCommunicator
}