package com.yankin.settings.impl.presentation.settings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yankin.common.resource_import.CommonRString
import com.yankin.coroutine.launchInJob
import com.yankin.coroutine.launchJob
import com.yankin.muscle_groups.api.models.MuscleGroupDomain
import com.yankin.muscle_groups.api.usecases.DeleteMuscleGroupUseCase
import com.yankin.muscle_groups.api.usecases.GetAllMuscleGroupStreamUseCase
import com.yankin.muscle_groups.api.usecases.RecoverDefaultMuscleGroupListUseCase
import com.yankin.muscle_groups.api.usecases.SaveMuscleGroupUseCase
import com.yankin.preferences.AppSettings
import com.yankin.resource_manager.api.ResourceManager
import com.yankin.settings.impl.presentation.settings.mappers.toExercisePatternListUiState
import com.yankin.settings.impl.presentation.settings.models.SettingsEvent
import com.yankin.settings.impl.presentation.settings.models.SettingsStateModel
import com.yankin.settings.impl.presentation.settings.models.SettingsUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

internal class SettingsViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val appSettings: AppSettings,
    private val deleteMuscleGroupUseCase: DeleteMuscleGroupUseCase,
    private val saveMuscleGroupUseCase: SaveMuscleGroupUseCase,
    private val recoverDefaultMuscleGroupListUseCase: RecoverDefaultMuscleGroupListUseCase,
    getAllMuscleGroupStreamUseCase: GetAllMuscleGroupStreamUseCase,
    private val resourceManager: ResourceManager,
) : ViewModel() {

    private val settingsState = MutableStateFlow(
        SettingsStateModel(
            reps = savedStateHandle.get<Int>(KEY_SETTINGS_REPS) ?: 0,
            weight = savedStateHandle.get<Double>(KEY_SETTINGS_WEIGHT) ?: 0.0,
            isLastTrainingTop = true,
            muscleGroupList = listOf(),
            muscleGroupNameByUser = savedStateHandle.get<String>(KEY_MUSCLE_GROUP_NAME) ?: "",
        )
    )
    private val settingsEventState: MutableStateFlow<SettingsEvent> =
        MutableStateFlow(SettingsEvent.Default)

    init {
        appSettings.getRepsStream().onEach { reps ->
            settingsState.update { stateModel -> stateModel.copy(reps = reps.getReps()) }
        }.launchInJob(scope = viewModelScope, catchBlock = Throwable::printStackTrace)

        appSettings.getWeightStream().onEach { weight ->
            settingsState.update { stateModel -> stateModel.copy(weight = weight.getWeight()) }
        }.launchInJob(scope = viewModelScope, catchBlock = Throwable::printStackTrace)

        appSettings.orderAddedFlow().onEach { isTopOrder ->
            settingsState.update { stateModel -> stateModel.copy(isLastTrainingTop = isTopOrder) }
        }.launchInJob(scope = viewModelScope, catchBlock = Throwable::printStackTrace)

        getAllMuscleGroupStreamUseCase.invoke().onEach { muscleGroupList ->
            settingsState.update { stateModel -> stateModel.copy(muscleGroupList = muscleGroupList) }
        }.launchInJob(scope = viewModelScope, catchBlock = Throwable::printStackTrace)
    }

    fun getSettingsUiStream(): Flow<SettingsUiState> =
        settingsState.map { stateModel ->
            stateModel.toExercisePatternListUiState(resourceManager)
        }

    fun getSettingsEventStream(): Flow<SettingsEvent> = settingsEventState
        .filterNot { event -> event is SettingsEvent.Default }

    fun onEventHandle() {
        settingsEventState.value = SettingsEvent.Default
    }

    fun onWeightUpClick() {
        settingsState.update { stateModel ->
            stateModel.copy(weight = stateModel.weight + 1).also { updatedStateModel ->
                savedStateHandle[KEY_SETTINGS_WEIGHT] = updatedStateModel.weight
            }
        }
    }

    fun onWeightDownClick() {
        settingsState.update { stateModel ->
            stateModel.copy(
                weight = if (stateModel.weight >= 1.0) stateModel.weight - 1 else 0.0
            ).also { updatedStateModel ->
                savedStateHandle[KEY_SETTINGS_WEIGHT] = updatedStateModel.weight
            }
        }
    }

    fun onRepsCountUpClick() {
        settingsState.update { stateModel ->
            stateModel.copy(reps = stateModel.reps + 1).also { updatedStateModel ->
                savedStateHandle[KEY_SETTINGS_REPS] = updatedStateModel.reps
            }
        }
    }

    fun onRepsCountDownClick() {
        settingsState.update { stateModel ->
            stateModel.copy(
                reps = if (stateModel.reps >= 1) stateModel.reps - 1 else 0
            ).also { updatedStateModel ->
                savedStateHandle[KEY_SETTINGS_REPS] = updatedStateModel.reps
            }
        }
    }

    fun onMuscleGroupNameChange(muscleGroupName: CharSequence) {
        settingsState.update { stateModel ->
            stateModel.copy(muscleGroupNameByUser = muscleGroupName.toString()).also { updatedStateModel ->
                savedStateHandle[KEY_MUSCLE_GROUP_NAME] = updatedStateModel.muscleGroupNameByUser
            }
        }
    }

    fun onWeightChange(weight: CharSequence) {
        if (weight.isEmpty()) return
        settingsState.update { stateModel ->
            stateModel.copy(weight = weight.toString().toDouble()).also { updatedStateModel ->
                savedStateHandle[KEY_SETTINGS_WEIGHT] = updatedStateModel.weight
            }
        }
    }

    fun onRepsChange(reps: CharSequence) {
        if (reps.isEmpty()) return
        settingsState.update { stateModel ->
            stateModel.copy(reps = reps.toString().toInt()).also { updatedStateModel ->
                savedStateHandle[KEY_SETTINGS_REPS] = updatedStateModel.reps
            }
        }
    }

    fun onSaveDefaultValuesClick() {
        when {
            settingsState.value.weight == 0.0 && settingsState.value.reps == 0 -> {
                settingsEventState.value = SettingsEvent.ShowInfoToast(
                    info = resourceManager.getString(CommonRString.the_weight_and_reoccurrence_fields_are_empty)
                )
            }

            settingsState.value.weight == 0.0 && settingsState.value.reps != 0 -> {
                viewModelScope.launchJob(catchBlock = Throwable::printStackTrace) {
                    appSettings.setReps(settingsState.value.reps)
                }
                settingsEventState.value = SettingsEvent.ShowInfoToast(
                    info = resourceManager.getString(CommonRString.reoccurrences_were_saved)
                )
            }

            settingsState.value.weight != 0.0 && settingsState.value.reps == 0 -> {
                viewModelScope.launchJob(catchBlock = Throwable::printStackTrace) {
                    appSettings.setWeight(settingsState.value.weight)
                }
                settingsEventState.value = SettingsEvent.ShowInfoToast(
                    info = resourceManager.getString(CommonRString.weight_was_saved)
                )
            }

            else -> {
                viewModelScope.launchJob(catchBlock = Throwable::printStackTrace) {
                    appSettings.setReps(settingsState.value.reps)
                    appSettings.setWeight(settingsState.value.weight)
                }
                settingsEventState.value = SettingsEvent.ShowInfoToast(
                    info = resourceManager.getString(CommonRString.default_values_were_saved)
                )
            }
        }
    }

    fun onSwitchOrderClick(isChecked: Boolean) {
        viewModelScope.launchJob(catchBlock = Throwable::printStackTrace) {
            appSettings.setOrderAdded(isChecked)
        }
    }

    fun onMuscleGroupClick(muscleGroupId: Long) {
        viewModelScope.launchJob(catchBlock = Throwable::printStackTrace) {
            deleteMuscleGroupUseCase.invoke(muscleGroupId)
        }
    }

    fun onSaveMuscleGroupClick() {
        if (settingsState.value.muscleGroupNameByUser.isNotEmpty()) {
            viewModelScope.launchJob(catchBlock = Throwable::printStackTrace) {
                saveMuscleGroupUseCase.invoke(
                    muscleGroupDomain = MuscleGroupDomain(
                        id = 0,
                        nameMuscleGroup = settingsState.value.muscleGroupNameByUser,
                        isDefault = false,
                        deleted = false,
                    )
                )
            }
            settingsState.update { stateModel ->
                stateModel.copy(muscleGroupNameByUser = "").also { updatedStateModel ->
                    savedStateHandle[KEY_MUSCLE_GROUP_NAME] = updatedStateModel.muscleGroupNameByUser
                }
            }
        } else {
            settingsEventState.value = SettingsEvent.ShowInfoToast(
                info = resourceManager.getString(CommonRString.the_name_muscle_group_is_empty)
            )
        }
    }

    fun onInformationClick() {
        settingsEventState.value = SettingsEvent.NavigateToInformation
    }

    fun onRecoverMuscleGroupsClick() {
        settingsEventState.value = SettingsEvent.RecoverMuscleGroupsDialog(
            title = resourceManager.getString(CommonRString.title_recover_dialog),
            message = resourceManager.getString(CommonRString.message_recover_dialog),
            positiveButton = resourceManager.getString(CommonRString.yes),
            negativeButton = resourceManager.getString(CommonRString.no)
        )
    }

    fun recoverValuesMuscleGroups() {
        viewModelScope.launchJob(catchBlock = Throwable::printStackTrace) {
            recoverDefaultMuscleGroupListUseCase.invoke()
        }
    }

    fun onSettingExercisePatternClick() {
        settingsEventState.value = SettingsEvent.NavigateToExercisePattern
    }

    private fun Double.getWeight(): Double =
        if (savedStateHandle.contains(KEY_SETTINGS_WEIGHT)) settingsState.value.weight else this

    private fun Int.getReps(): Int =
        if (savedStateHandle.contains(KEY_SETTINGS_REPS)) settingsState.value.reps else this

    companion object {

        private const val KEY_MUSCLE_GROUP_NAME = "KEY_MUSCLE_GROUP_NAME"
        private const val KEY_SETTINGS_REPS = "KEY_SETTINGS_REPS"
        private const val KEY_SETTINGS_WEIGHT = "KEY_SETTINGS_WEIGHT"
    }
}