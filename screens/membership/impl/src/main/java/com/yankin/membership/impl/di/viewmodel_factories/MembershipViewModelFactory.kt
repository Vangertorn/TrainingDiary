package com.yankin.membership.impl.di.viewmodel_factories

import com.yankin.common.viewmodel.ViewModelFactory
import com.yankin.membership.impl.navigation.MembershipParcelableParams
import com.yankin.membership.impl.presentation.membership_info.MembershipViewModel
import dagger.assisted.AssistedFactory

@AssistedFactory
internal interface MembershipViewModelFactory :
    ViewModelFactory.ViewModelParamsFactory<MembershipViewModel, MembershipParcelableParams>