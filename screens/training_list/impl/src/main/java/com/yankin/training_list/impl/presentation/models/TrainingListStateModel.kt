package com.yankin.training_list.impl.presentation.models

import com.yankin.membership.api.models.MembershipDomain

internal data class TrainingListStateModel (
    val membership: MembershipDomain?,
)