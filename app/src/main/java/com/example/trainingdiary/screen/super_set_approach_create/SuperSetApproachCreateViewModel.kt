package com.example.trainingdiary.screen.super_set_approach_create

import androidx.lifecycle.asLiveData
import com.example.trainingdiary.datastore.AppSettings
import com.example.trainingdiary.models.Approach
import com.example.trainingdiary.models.Exercise
import com.example.trainingdiary.models.info.ViewHolderTypes
import com.example.trainingdiary.repository.ApproachRepository
import com.example.trainingdiary.repository.ExerciseRepository
import com.example.trainingdiary.repository.SuperSetRepository
import com.example.trainingdiary.support.CoroutineViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SuperSetApproachCreateViewModel(
    exerciseRepository: ExerciseRepository,
    private val superSetRepository: SuperSetRepository,
    private val appSettings: AppSettings,
    private val approachRepository: ApproachRepository
) : CoroutineViewModel() {


    val exerciseInfoLiveData = superSetRepository.currentExerciseInfoInSuperSetFlow.asLiveData()

    val reoccurrencesLiveData = appSettings.reoccurrencesFlow().asLiveData()
    val weightLiveData = appSettings.weightFlow().asLiveData()
    val approachLiveData = approachRepository.currentApproachFlow.asLiveData()


    fun exerciseInfoFirst(): ViewHolderTypes.ExerciseInfo {
        return runBlocking {
            return@runBlocking superSetRepository.currentExerciseInfoInSuperSetFlow.first()[0]
        }
    }

    fun deleteApproach(approach: Approach) {
        launch {
            approachRepository.deleteApproach(approach)
        }
    }

    fun rememberIdExercise(exercise: Exercise) {
        launch {
            appSettings.setIdExercise(exercise.id)
        }
    }
    fun addNewApproach(approach: Approach) {
        launch {
            approachRepository.saveApproach(approach)
        }

    }

}