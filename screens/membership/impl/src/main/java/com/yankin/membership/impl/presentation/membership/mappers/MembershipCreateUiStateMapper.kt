package com.yankin.membership.impl.presentation.membership.mappers

import com.yankin.date.DateFormatter
import com.yankin.membership.impl.presentation.membership.models.MembershipCreateStateModel
import com.yankin.membership.impl.presentation.membership.models.MembershipCreateUiState

internal fun MembershipCreateStateModel.toMembershipCreateUiState(): MembershipCreateUiState {

    return MembershipCreateUiState(
        selectedDate = DateFormatter.longToDate(selectedDate),
        trainingCount = trainingCount.toString(),
        isUnlimitedCheck = isUnlimited,
        isIndefiniteCheck = isIndefinite,
    )
}