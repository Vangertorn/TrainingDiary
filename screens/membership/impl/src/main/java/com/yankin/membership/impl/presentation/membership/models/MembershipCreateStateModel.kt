package com.yankin.membership.impl.presentation.membership.models

import com.yankin.date.Timestamp

internal data class MembershipCreateStateModel(
    val selectedDate: Timestamp.Milliseconds,
    val trainingCount: Int,
    val isUnlimited: Boolean,
    val isIndefinite: Boolean,
)