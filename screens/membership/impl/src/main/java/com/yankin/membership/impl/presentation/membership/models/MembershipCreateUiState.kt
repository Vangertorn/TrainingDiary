package com.yankin.membership.impl.presentation.membership.models

import java.util.Date

internal data class MembershipCreateUiState(
    val trainingCount: String,
    val isUnlimitedCheck: Boolean,
    val isIndefiniteCheck: Boolean,
    val selectedDate: Date,
)