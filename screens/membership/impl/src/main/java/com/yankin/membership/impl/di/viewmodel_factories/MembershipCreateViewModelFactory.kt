package com.yankin.membership.impl.di.viewmodel_factories

import com.yankin.common.viewmodel.ViewModelFactory
import com.yankin.membership.impl.presentation.membership.MembershipCreateViewModel
import dagger.assisted.AssistedFactory

@AssistedFactory
internal interface MembershipCreateViewModelFactory :
    ViewModelFactory.ViewModelWithoutParamsFactory<MembershipCreateViewModel>