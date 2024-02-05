package com.yankin.settings.impl.di

import com.yankin.common.viewmodel.ViewModelFactory
import com.yankin.settings.impl.navigation.ExercisePatternCreateDialogParams
import com.yankin.settings.impl.presentation.exercise_pattern_create.ExercisePatternCreateViewModel
import dagger.assisted.AssistedFactory

@AssistedFactory
internal interface ExercisePatternCreateViewModelFactory :
    ViewModelFactory.ViewModelParamsFactory<ExercisePatternCreateViewModel, ExercisePatternCreateDialogParams>