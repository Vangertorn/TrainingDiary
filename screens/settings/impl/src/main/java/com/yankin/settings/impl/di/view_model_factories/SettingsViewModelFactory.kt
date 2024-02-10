package com.yankin.settings.impl.di.view_model_factories

import com.yankin.common.viewmodel.ViewModelFactory
import com.yankin.settings.impl.presentation.settings.SettingsViewModel
import dagger.assisted.AssistedFactory

@AssistedFactory
internal interface SettingsViewModelFactory :
    ViewModelFactory.ViewModelWithoutParamsFactory<SettingsViewModel>