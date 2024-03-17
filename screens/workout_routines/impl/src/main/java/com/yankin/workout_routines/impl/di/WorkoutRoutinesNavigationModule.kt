package com.yankin.workout_routines.impl.di

import com.yankin.navigation.NavigationNode
import com.yankin.workout_routines.impl.navigation.WorkoutRoutinesNavigationNode
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
internal interface WorkoutRoutinesNavigationModule {

    @IntoSet
    @Binds
    fun bindNavigationNode(workoutRoutinesNavigationNode: WorkoutRoutinesNavigationNode): NavigationNode
}