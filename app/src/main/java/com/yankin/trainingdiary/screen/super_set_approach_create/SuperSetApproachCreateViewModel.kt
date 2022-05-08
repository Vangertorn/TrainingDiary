package com.yankin.trainingdiary.screen.super_set_approach_create

import androidx.lifecycle.asLiveData
import com.yankin.trainingdiary.datastore.AppSettings
import com.yankin.trainingdiary.models.Approach
import com.yankin.trainingdiary.models.Exercise
import com.yankin.trainingdiary.models.info.ViewHolderTypes
import com.yankin.trainingdiary.repository.ApproachRepository
import com.yankin.trainingdiary.repository.SuperSetRepository
import com.yankin.trainingdiary.support.CoroutineViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SuperSetApproachCreateViewModel(
    private val superSetRepository: SuperSetRepository,
    private val appSettings: AppSettings,
    private val approachRepository: ApproachRepository
) : CoroutineViewModel() {

    val exerciseInfoLiveData = superSetRepository.currentExerciseInfoInSuperSetFlow.asLiveData()

    val reoccurrencesLiveData = appSettings.reoccurrencesFlow().asLiveData()
    val weightLiveData = appSettings.weightFlow().asLiveData()

    @ExperimentalCoroutinesApi
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