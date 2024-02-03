package com.yankin.membership.api.models

import com.yankin.date.Timestamp

data class MembershipDomain(
    val id: Long,
    val trainingCount: Int?,
    val startDate: Timestamp.Milliseconds,
    val endDate: Timestamp.Milliseconds?,
    val accountedTrainingIdList: List<Long>,
)
