package com.yankin.membership.impl.presentation.membership_info

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yankin.coroutine.launchJob
import com.yankin.membership.api.usecases.DeleteMembershipByIdUseCase
import com.yankin.membership.api.usecases.GetMembershipByIdUseCase
import com.yankin.membership.impl.navigation.MembershipParcelableParams
import com.yankin.membership.impl.presentation.membership_info.mappers.toMembershipUiState
import com.yankin.membership.impl.presentation.membership_info.models.MembershipStateModel
import com.yankin.membership.impl.presentation.membership_info.models.MembershipUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

internal class MembershipViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val getMembershipByIdUseCase: GetMembershipByIdUseCase,
    private val deleteMembershipByIdUseCase: DeleteMembershipByIdUseCase,
    @Assisted private val params: MembershipParcelableParams,
) : ViewModel() {

    private val membershipState = MutableStateFlow(MembershipStateModel(membership = null))

    init {
        viewModelScope.launchJob(catchBlock = Throwable::printStackTrace) {
            val membership = getMembershipByIdUseCase.invoke(params.membershipId)
            membershipState.update { stateModel ->
                stateModel.copy(membership = membership)
            }
        }
    }

    fun getMembershipUiStream(): Flow<MembershipUiState> = membershipState.map { stateModel ->
        stateModel.toMembershipUiState()
    }

    fun onDeleteMembershipClick() {
        viewModelScope.launchJob(catchBlock = Throwable::printStackTrace) {
            membershipState.value.membership?.id?.let { membershipId ->
                deleteMembershipByIdUseCase.invoke(membershipId)
            }
        }
    }
}
