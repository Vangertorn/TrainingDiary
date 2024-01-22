package com.yankin.settings.impl.presentation.settings

import android.content.Context
import androidx.lifecycle.asLiveData
import com.yankin.common.viewmodel.CoroutineViewModel
import com.yankin.muscle_groups.api.usecases.DeleteMuscleGroupUseCase
import com.yankin.muscle_groups.api.usecases.GetCurrentMuscleGroupStreamUseCase
import com.yankin.muscle_groups.api.usecases.RecoverDefaultMuscleGroupListUseCase
import com.yankin.muscle_groups.api.usecases.SaveMuscleGroupUseCase
import com.yankin.preferences.AppSettings
import com.yankin.settings.impl.presentation.MuscleGroup
import com.yankin.settings.impl.presentation.toDomain
import com.yankin.trainingdiary.settings.impl.R
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
    getCurrentMuscleGroupStreamUseCase: GetCurrentMuscleGroupStreamUseCase,
    @ApplicationContext private val context: Context,
) : CoroutineViewModel() {

    val reoccurrencesLiveData = appSettings.reoccurrencesFlow().asLiveData()
    val weightLiveData = appSettings.weightFlow().asLiveData()
    val switchOrderLiveData = appSettings.orderAddedFlow().asLiveData()
    val muscleGroupLiveData = getCurrentMuscleGroupStreamUseCase.invoke().asLiveData()

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
                    MuscleGroup(nameMuscleGroup = context.getString(R.string.legs), factorySettings = true),
                    MuscleGroup(
                        nameMuscleGroup = context.getString(R.string.all_muscle_groups),
                        factorySettings = true
                    ),
                    MuscleGroup(nameMuscleGroup = context.getString(R.string.breast), factorySettings = true),
                    MuscleGroup(nameMuscleGroup = context.getString(R.string.biceps), factorySettings = true),
                    MuscleGroup(
                        nameMuscleGroup = context.getString(R.string.shoulders),
                        factorySettings = true
                    ),
                    MuscleGroup(nameMuscleGroup = context.getString(R.string.back), factorySettings = true),
                    MuscleGroup(nameMuscleGroup = context.getString(R.string.triceps), factorySettings = true)
                ).map { it.toDomain() }
            )
        }
    }
}
