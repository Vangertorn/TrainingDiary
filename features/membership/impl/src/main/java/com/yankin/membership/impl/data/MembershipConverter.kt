package com.yankin.membership.impl.data

import com.yankin.date.Timestamp
import com.yankin.membership.api.models.MembershipDomain
import com.yankin.room.entity.MembershipEntity

fun MembershipEntity.toDomain() = MembershipDomain(
    id = id,
    trainingCount = trainingCount,
    startDate = Timestamp.Milliseconds(value = startDate),
    endDate = endDate?.let { safeEndDate ->
        Timestamp.Milliseconds(value = safeEndDate)
    },
    accountedTrainingIdList = accountedTrainingIdList,
)

fun MembershipDomain.toEntity() = MembershipEntity(
    id = id,
    trainingCount = trainingCount,
    trainingCountPast = accountedTrainingIdList.size,
    startDate = startDate.getMilliseconds(),
    endDate = endDate?.getMilliseconds(),
    accountedTrainingIdList = accountedTrainingIdList

)