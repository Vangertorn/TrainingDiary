package com.yankin.workout_routines.impl.di

import com.yankin.workout_routines.api.navigation.WorkoutRoutinesCommunicator
import com.yankin.workout_routines.impl.navigation.WorkoutRoutinesCommunicatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
interface WorkoutRoutinesModule {
    @Binds
    fun bindCommunicator(
        workoutRoutinesCommunicatorImpl: WorkoutRoutinesCommunicatorImpl
    ): WorkoutRoutinesCommunicator
}