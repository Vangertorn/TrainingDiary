package com.yankin.membership.impl.presentation.membership

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yankin.coroutine.launchJob
import com.yankin.date.Timestamp
import com.yankin.membership.api.models.MembershipDomain
import com.yankin.membership.api.usecases.AddMembershipUseCase
import com.yankin.membership.impl.presentation.membership.mappers.toMembershipCreateUiState
import com.yankin.membership.impl.presentation.membership.models.MembershipCreateEvent
import com.yankin.membership.impl.presentation.membership.models.MembershipCreateStateModel
import com.yankin.membership.impl.presentation.membership.models.MembershipCreateUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.util.Calendar
import java.util.Date

internal class MembershipCreateViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val addMembershipUseCase: AddMembershipUseCase,
) : ViewModel() {

    private val membershipCreateState = MutableStateFlow(
        MembershipCreateStateModel(
            selectedDate = Timestamp.Milliseconds(
                savedStateHandle.get<Long>(KEY_MEMBERSHIP_DATE) ?: Calendar.getInstance().time.time
            ),
            trainingCount = savedStateHandle.get<Int>(KEY_MEMBERSHIP_TRAINING_COUNT) ?: 0,
            isUnlimited = savedStateHandle.get<Boolean>(KEY_MEMBERSHIP_UNLIMITED) ?: false,
            isIndefinite = savedStateHandle.get<Boolean>(KEY_MEMBERSHIP_INDEFINITE) ?: false,
        )
    )

    private val membershipCreateEventState: MutableStateFlow<MembershipCreateEvent> =
        MutableStateFlow(MembershipCreateEvent.Default)

    fun getMembershipCreateUiStream(): Flow<MembershipCreateUiState> = membershipCreateState.map { stateModel ->
        stateModel.toMembershipCreateUiState()
    }

    fun getMembershipCreateEventStream(): Flow<MembershipCreateEvent> = membershipCreateEventState
        .filterNot { event -> event is MembershipCreateEvent.Default }

    fun onEventHandle() {
        membershipCreateEventState.value = MembershipCreateEvent.Default
    }

    fun onTrainingCountChange(count: CharSequence) {
        val trainingCount = count.toString().toInt()
        if (trainingCount != membershipCreateState.value.trainingCount) {
            membershipCreateState.update { stateModel ->
                stateModel.copy(trainingCount = trainingCount).also { updatedStateModel ->
                    savedStateHandle[KEY_MEMBERSHIP_TRAINING_COUNT] = updatedStateModel.trainingCount
                }
            }
        }
    }

    fun onTrainingCountUpClick() {
        membershipCreateState.update { stateModel ->
            stateModel.copy(trainingCount = stateModel.trainingCount + 1).also { updatedStateModel ->
                savedStateHandle[KEY_MEMBERSHIP_TRAINING_COUNT] = updatedStateModel.trainingCount
            }
        }
    }

    fun onTrainingCountDownClick() {
        membershipCreateState.update { stateModel ->
            stateModel.copy(
                trainingCount = if (stateModel.trainingCount > 0) stateModel.trainingCount - 1 else 0
            ).also { updatedStateModel ->
                savedStateHandle[KEY_MEMBERSHIP_TRAINING_COUNT] = updatedStateModel.trainingCount
            }
        }
    }

    fun onDateChange(date: Date) {
        if (date.time < Calendar.getInstance().time.time) {
            membershipCreateEventState.value = MembershipCreateEvent.EndDateError
        } else {
            membershipCreateState.update { stateModel ->
                stateModel.copy(
                    selectedDate = Timestamp.Milliseconds(date.time)
                ).also { updatedStateModel ->
                    savedStateHandle[KEY_MEMBERSHIP_DATE] = updatedStateModel.selectedDate.value
                }
            }
        }
    }

    fun onUnlimitedClick() {
        membershipCreateState.update { stateModel ->
            stateModel.copy(
                isUnlimited = !stateModel.isUnlimited
            ).also { updatedStateModel ->
                savedStateHandle[KEY_MEMBERSHIP_UNLIMITED] = updatedStateModel.isUnlimited
            }
        }
    }

    fun onIndefiniteClick() {
        membershipCreateState.update { stateModel ->
            stateModel.copy(
                isIndefinite = !stateModel.isIndefinite
            ).also { updatedStateModel ->
                savedStateHandle[KEY_MEMBERSHIP_INDEFINITE] = updatedStateModel.isIndefinite
            }
        }
    }

    fun onSaveMembershipClick() {
        if (!membershipCreateState.value.isUnlimited && membershipCreateState.value.trainingCount == 0) {
            membershipCreateEventState.value = MembershipCreateEvent.NotEnoughTrainingCount
            return
        }
        viewModelScope.launchJob(catchBlock = Throwable::printStackTrace) {
            addMembershipUseCase.invoke(
                with(membershipCreateState.value) {
                    MembershipDomain(
                        id = 0,
                        trainingCount = if (isUnlimited) null else trainingCount,
                        startDate = Timestamp.Milliseconds(Calendar.getInstance().time.time),
                        endDate = if (isIndefinite) null else selectedDate,
                        accountedTrainingIdList = listOf(),
                    )
                }

            )
            membershipCreateEventState.value = MembershipCreateEvent.Dismiss
        }
    }

    companion object {

        private const val KEY_MEMBERSHIP_INDEFINITE = "KEY_MEMBERSHIP_INDEFINITE"
        private const val KEY_MEMBERSHIP_TRAINING_COUNT = "KEY_MEMBERSHIP_TRAINING_COUNT"
        private const val KEY_MEMBERSHIP_DATE = "KEY_MEMBERSHIP_DATE"
        private const val KEY_MEMBERSHIP_UNLIMITED = "KEY_MEMBERSHIP_UNLIMITED"
    }
}
