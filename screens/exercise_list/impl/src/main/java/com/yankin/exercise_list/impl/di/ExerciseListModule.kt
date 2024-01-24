package com.yankin.exercise_list.impl.di

import com.yankin.exercise_list.api.navigation.ExerciseListCommunicator
import com.yankin.exercise_list.impl.navigation.ExerciseListCommunicatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
interface ExerciseListModule {
    @Binds
    fun bindCommunicator(exerciseListCommunicatorImpl: ExerciseListCommunicatorImpl): ExerciseListCommunicator
}