package com.yankin.trainingdiary.screen.super_set_approach_create

import androidx.lifecycle.asLiveData
import com.yankin.approach.api.usecases.DeleteApproachUseCase
import com.yankin.approach.api.usecases.GetCurrentApproachStreamUseCase
import com.yankin.approach.api.usecases.SaveApproachUseCase
import com.yankin.preferences.AppSettings
import com.yankin.trainingdiary.models.Approach
import com.yankin.trainingdiary.models.Exercise
import com.yankin.trainingdiary.models.converters.toDomain
import com.yankin.trainingdiary.models.converters.toModel
import com.yankin.trainingdiary.models.info.ViewHolderTypes
import com.yankin.trainingdiary.repository.SuperSetRepository
import com.yankin.trainingdiary.support.CoroutineViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SuperSetApproachCreateViewModel @Inject constructor(
    private val superSetRepository: SuperSetRepository,
    private val appSettings: AppSettings,
    getCurrentApproachStreamUseCase: GetCurrentApproachStreamUseCase,
    private val saveApproachUseCase: SaveApproachUseCase,
    private val deleteApproachUseCase: DeleteApproachUseCase,
) : CoroutineViewModel() {

    val exerciseInfoLiveData = superSetRepository.currentExerciseInfoInSuperSetFlow.asLiveData()

    val reoccurrencesLiveData = appSettings.reoccurrencesFlow().asLiveData()
    val weightLiveData = appSettings.weightFlow().asLiveData()

    @ExperimentalCoroutinesApi
    val approachLiveData = getCurrentApproachStreamUseCase.invoke().map {approachDomainList->
        approachDomainList.map { approachDomain -> approachDomain.toModel() }
    }.asLiveData()

    fun exerciseInfoFirst(): ViewHolderTypes.ExerciseInfo {
        return runBlocking {
            return@runBlocking superSetRepository.currentExerciseInfoInSuperSetFlow.first()[0]
        }
    }

    fun deleteApproach(approach: Approach) {
        launch {
            deleteApproachUseCase.invoke(approach = approach.toDomain())
        }
    }

    fun rememberIdExercise(exercise: Exercise) {
        launch {
            appSettings.setIdExercise(exercise.id)
        }
    }

    fun addNewApproach(approach: Approach) {
        launch {
            saveApproachUseCase.invoke(approach = approach.toDomain())
        }
    }
}
