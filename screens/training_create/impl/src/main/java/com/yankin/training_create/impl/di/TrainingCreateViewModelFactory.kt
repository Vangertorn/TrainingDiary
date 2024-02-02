package com.yankin.training_create.impl.di

import com.yankin.common.viewmodel.ViewModelFactory
import com.yankin.training_create.impl.navigation.TrainingCreateParcelableParams
import com.yankin.training_create.impl.presentation.TrainingCreateViewModel
import dagger.assisted.AssistedFactory

@AssistedFactory
internal interface TrainingCreateViewModelFactory :
    ViewModelFactory<TrainingCreateViewModel, TrainingCreateParcelableParams>