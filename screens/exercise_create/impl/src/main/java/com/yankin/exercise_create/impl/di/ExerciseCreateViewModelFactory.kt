package com.yankin.exercise_create.impl.di

import com.yankin.common.viewmodel.ViewModelFactory
import com.yankin.exercise_create.impl.navigation.ExerciseCreateParcelableParams
import com.yankin.exercise_create.impl.presentation.ExerciseCreateViewModel
import dagger.assisted.AssistedFactory

@AssistedFactory
internal interface ExerciseCreateViewModelFactory :
    ViewModelFactory.ViewModelParamsFactory<ExerciseCreateViewModel, ExerciseCreateParcelableParams>