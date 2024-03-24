package com.yankin.training_list.impl.presentation.models

import com.yankin.membership.api.models.MembershipDomain
import com.yankin.training.api.models.TrainingDomain

internal data class TrainingListStateModel (
    val membership: MembershipDomain?,
    val trainings: List<TrainingDomain>,
    val isLastTrainUp: Boolean,
    val isScrollNeed: Boolean,
)