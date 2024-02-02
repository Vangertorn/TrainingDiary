package com.yankin.settings.impl.presentation.settings

import android.content.Context
import androidx.lifecycle.asLiveData
import com.yankin.common.resource_import.CommonRString
import com.yankin.common.viewmodel.CoroutineViewModel
import com.yankin.muscle_groups.api.usecases.DeleteMuscleGroupUseCase
import com.yankin.muscle_groups.api.usecases.GetAllMuscleGroupStreamUseCase
import com.yankin.muscle_groups.api.usecases.RecoverDefaultMuscleGroupListUseCase
import com.yankin.muscle_groups.api.usecases.SaveMuscleGroupUseCase
import com.yankin.preferences.AppSettings
import com.yankin.settings.impl.presentation.MuscleGroup
import com.yankin.settings.impl.presentation.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val appSettings: AppSettings,
    private val deleteMuscleGroupUseCase: DeleteMuscleGroupUseCase,
    private val saveMuscleGroupUseCase: SaveMuscleGroupUseCase,
    private val recoverDefaultMuscleGroupListUseCase: RecoverDefaultMuscleGroupListUseCase,
    getAllMuscleGroupStreamUseCase: GetAllMuscleGroupStreamUseCase,
    @ApplicationContext private val context: Context,
) : CoroutineViewModel() {

    val reoccurrencesLiveData = appSettings.reoccurrencesFlow().asLiveData()
    val weightLiveData = appSettings.weightFlow().asLiveData()
    val switchOrderLiveData = appSettings.orderAddedFlow().asLiveData()
    val muscleGroupLiveData = getAllMuscleGroupStreamUseCase.invoke().asLiveData()

    fun saveReoccurrences(reoccurrences: String) {
        launch {
            appSettings.setReoccurrences(reoccurrences)
        }
    }

    fun saveWeight(weight: String) {
        launch {
            appSettings.setWeight(weight)
        }
    }

    fun saveOrderAdded(order: Boolean) {
        launch {
            appSettings.setOrderAdded(order)
        }
    }

    fun deleteMuscleGroup(muscleGroup: MuscleGroup) {
        launch {
            deleteMuscleGroupUseCase.invoke(muscleGroup.toDomain())
        }
    }

    fun saveMuscleGroup(muscleGroup: MuscleGroup) {
        launch {
            saveMuscleGroupUseCase.invoke(muscleGroup.toDomain())
        }
    }

    fun recoverValuesMuscleGroups() {
        launch {
            recoverDefaultMuscleGroupListUseCase.invoke(
                listOf(
                    MuscleGroup(nameMuscleGroup = context.getString(CommonRString.legs), factorySettings = true),
                    MuscleGroup(
                        nameMuscleGroup = context.getString(CommonRString.all_muscle_groups),
                        factorySettings = true
                    ),
                    MuscleGroup(nameMuscleGroup = context.getString(CommonRString.breast), factorySettings = true),
                    MuscleGroup(nameMuscleGroup = context.getString(CommonRString.biceps), factorySettings = true),
                    MuscleGroup(
                        nameMuscleGroup = context.getString(CommonRString.shoulders),
                        factorySettings = true
                    ),
                    MuscleGroup(nameMuscleGroup = context.getString(CommonRString.back), factorySettings = true),
                    MuscleGroup(nameMuscleGroup = context.getString(CommonRString.triceps), factorySettings = true)
                ).map { it.toDomain() }
            )
        }
    }
}
