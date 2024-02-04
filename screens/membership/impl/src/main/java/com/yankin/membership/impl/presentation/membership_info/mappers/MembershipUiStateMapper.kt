package com.yankin.membership.impl.presentation.membership_info.mappers

import com.yankin.date.DateFormatter.D
import com.yankin.date.DateFormatter.DD_POINT_MM_POINT_YY
import com.yankin.date.DateFormatter.toDateStringOrEmpty
import com.yankin.membership.impl.presentation.membership_info.models.MembershipStateModel
import com.yankin.membership.impl.presentation.membership_info.models.MembershipUiState

internal fun MembershipStateModel.toMembershipUiState(): MembershipUiState {

    return MembershipUiState(
        trainingLeft = membership?.let { membership ->
            membership.trainingCount?.let { trainingCount ->
                (trainingCount - membership.accountedTrainingIdList.size).toString()
            } ?: "ထ"
        } ?: "",
        daysLeft = membership?.let { membership ->
            membership.endDate?.let { endDate ->
                (endDate - membership.startDate).toDateStringOrEmpty(dateFormat = D)
            } ?: "ထ"
        } ?: "",
        endDate = membership?.let { membership ->
            membership.endDate?.toDateStringOrEmpty(dateFormat = DD_POINT_MM_POINT_YY) ?: "ထ"
        } ?: ""
    )
}