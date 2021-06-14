package com.example.trainingdiary.screen.training_create

import androidx.lifecycle.asLiveData
import com.example.trainingdiary.models.MuscleGroup
import com.example.trainingdiary.models.Training
import com.example.trainingdiary.repository.MuscleGroupRepository
import com.example.trainingdiary.repository.TrainingRepository
import com.example.trainingdiary.support.CoroutineViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.StringBuilder

class TrainingCreateViewModel(
    private val trainingRepository: TrainingRepository,
    private val muscleGroupRepository: MuscleGroupRepository
) :
    CoroutineViewModel() {

    fun addNewTraining(training: Training) {
        launch {
            trainingRepository.saveTraining(training)
        }
    }
    fun updateTraining(training: Training){
        launch {
            trainingRepository.updateTraining(training)
        }
    }

    val muscleGroupLiveData = muscleGroupRepository.currentMuscleGroupFlow.asLiveData()


    fun addMuscleGroups(selectedPositions: List<Int>): String {
        val stringBuilder = StringBuilder()
        val listMuscleGroups = runBlocking {
            muscleGroupRepository.getMuscleGroups()
        }
        for (index in listMuscleGroups.indices) {
            if (index in selectedPositions) {
                stringBuilder.append(listMuscleGroups[index].nameMuscleGroup)
                stringBuilder.append(", ")
            }
        }
        return stringBuilder.toString().removeSuffix(", ")
    }

}