package com.example.trainingdiary.screen.training_list

import androidx.lifecycle.asLiveData
import com.example.trainingdiary.dao.database.MuscleGroupDao
import com.example.trainingdiary.datastore.AppSettings
import com.example.trainingdiary.models.MuscleGroup
import com.example.trainingdiary.models.Training
import com.example.trainingdiary.repository.MuscleGroupRepository
import com.example.trainingdiary.repository.TrainingRepository
import com.example.trainingdiary.support.CoroutineViewModel
import kotlinx.coroutines.launch

class TrainingListViewModel(
    private val trainingRepository: TrainingRepository,
    private val appSettings: AppSettings,
    private val muscleGroupRepository: MuscleGroupRepository
) : CoroutineViewModel() {
    val trainingLiveData = trainingRepository.currentTrainingFlow.asLiveData()

    fun deleteTraining(position: Int) {
        launch {
            trainingRepository.deleteTraining(trainingLiveData.value?.get(position)!!)
        }
    }

    fun rememberIdTraining(training: Training) {
        launch {
            appSettings.setIdTraining(training.id)
        }
    }

    init {
        launch {
            muscleGroupRepository.saveDefaultValues(
                listOf(
                    MuscleGroup(nameMuscleGroup = "Legs", factorySettings = true),
                    MuscleGroup(nameMuscleGroup = "All muscle groups", factorySettings = true),
                    MuscleGroup(nameMuscleGroup = "Breast", factorySettings = true),
                    MuscleGroup(nameMuscleGroup = "Biceps", factorySettings = true),
                    MuscleGroup(nameMuscleGroup = "Shoulders", factorySettings = true),
                    MuscleGroup(nameMuscleGroup = "Back", factorySettings = true),
                    MuscleGroup(nameMuscleGroup = "Triceps", factorySettings = true)
                )
            )
        }

    }
}