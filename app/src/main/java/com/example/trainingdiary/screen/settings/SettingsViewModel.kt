package com.example.trainingdiary.screen.settings

import androidx.lifecycle.asLiveData
import com.example.trainingdiary.datastore.AppSettings
import com.example.trainingdiary.models.MuscleGroup
import com.example.trainingdiary.repository.MuscleGroupRepository
import com.example.trainingdiary.support.CoroutineViewModel
import kotlinx.coroutines.launch

class SettingsViewModel(
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

    fun recoverValuesMuscleGroups(){
        launch {
            muscleGroupRepository.recoverDefaultValues()
        }
    }

}