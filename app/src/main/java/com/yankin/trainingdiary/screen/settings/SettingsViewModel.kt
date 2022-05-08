package com.yankin.trainingdiary.screen.settings

import androidx.lifecycle.asLiveData
import com.yankin.trainingdiary.datastore.AppSettings
import com.yankin.trainingdiary.models.MuscleGroup
import com.yankin.trainingdiary.repository.MuscleGroupRepository
import com.yankin.trainingdiary.support.CoroutineViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val appSettings: AppSettings,
    private val muscleGroupRepository: MuscleGroupRepository
) : CoroutineViewModel() {

    val reoccurrencesLiveData = appSettings.reoccurrencesFlow().asLiveData()
    val weightLiveData = appSettings.weightFlow().asLiveData()
    val switchOrderLiveData = appSettings.orderAddedFlow().asLiveData()
    val muscleGroupLiveData = muscleGroupRepository.currentMuscleGroupFlow.asLiveData()

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
            muscleGroupRepository.deleteMuscleGroup(muscleGroup)
        }
    }

    fun saveMuscleGroup(muscleGroup: MuscleGroup) {
        launch {
            muscleGroupRepository.saveMuscleGroup(muscleGroup)
        }
    }

    fun recoverValuesMuscleGroups() {
        launch {
            muscleGroupRepository.recoverDefaultValues()
        }
    }
}