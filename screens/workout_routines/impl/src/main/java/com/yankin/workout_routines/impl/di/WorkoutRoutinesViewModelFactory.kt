package com.yankin.workout_routines.impl.di

import com.yankin.common.viewmodel.ViewModelFactory
import com.yankin.workout_routines.impl.navigation.WorkoutRoutinesParcelableParams
import com.yankin.workout_routines.impl.presentation.WorkoutRoutinesViewModel
import dagger.assisted.AssistedFactory

@AssistedFactory
internal interface WorkoutRoutinesViewModelFactory :
    ViewModelFactory.ViewModelParamsFactory<WorkoutRoutinesViewModel, WorkoutRoutinesParcelableParams>