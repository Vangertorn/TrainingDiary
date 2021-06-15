package com.example.trainingdiary.screen.approach_create

import androidx.lifecycle.asLiveData
import com.example.trainingdiary.datastore.AppSettings
import com.example.trainingdiary.models.Approach
import com.example.trainingdiary.models.Exercise
import com.example.trainingdiary.repository.ApproachRepository
import com.example.trainingdiary.repository.ExerciseRepository
import com.example.trainingdiary.support.CoroutineViewModel
import kotlinx.coroutines.launch

class ApproachCreateViewModel(
    private val approachRepository: ApproachRepository,
    private val exerciseRepository: ExerciseRepository,
    appSettings: AppSettings
) :
    CoroutineViewModel() {

    val approachLiveData = approachRepository.currentApproachFlow.asLiveData()
    val reoccurrencesLiveData = appSettings.reoccurrencesFlow().asLiveData()
    val weightLiveData = appSettings.weightFlow().asLiveData()

    fun addNewApproach(approach: Approach) {
        launch {
            approachRepository.saveApproach(approach)
        }

    }

    fun deleteApproach(approach: Approach) {
        launch {
            approachRepository.deleteApproach(approach)
        }
    }

    fun updateExercise(exercise: Exercise) {
        launch {
            exerciseRepository.updateExercise(
            exercise
                )

        }
    }

}