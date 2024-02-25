package com.yankin.approach_create.impl.di

import com.yankin.approach_create.impl.navigation.SetCreateParcelableParams
import com.yankin.approach_create.impl.presentation.SetCreateViewModel
import com.yankin.common.viewmodel.ViewModelFactory
import dagger.assisted.AssistedFactory

@AssistedFactory
internal interface SetCreateViewModelFactory :
    ViewModelFactory.ViewModelParamsFactory<SetCreateViewModel, SetCreateParcelableParams>