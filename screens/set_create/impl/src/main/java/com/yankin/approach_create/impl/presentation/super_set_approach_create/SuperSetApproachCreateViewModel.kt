package com.yankin.approach_create.impl.presentation.super_set_approach_create

import androidx.lifecycle.asLiveData
import com.yankin.set.api.usecases.DeleteSetUseCase
import com.yankin.set.api.usecases.GetSetListUseCase
import com.yankin.set.api.usecases.GetCurrentSetStreamUseCase
import com.yankin.set.api.usecases.SaveSetUseCase
import com.yankin.approach_create.impl.presentation.Approach
import com.yankin.approach_create.impl.presentation.Exercise
import com.yankin.approach_create.impl.presentation.ViewHolderTypes
import com.yankin.approach_create.impl.presentation.toDomain
import com.yankin.approach_create.impl.presentation.toModel
import com.yankin.common.viewmodel.CoroutineViewModel
import com.yankin.exercise.api.usecases.GetExerciseListBySuperSetIdStreamUseCase
import com.yankin.preferences.AppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SuperSetApproachCreateViewModel @Inject constructor(
    private val appSettings: AppSettings,
    getCurrentSetStreamUseCase: GetCurrentSetStreamUseCase,
    private val saveSetUseCase: SaveSetUseCase,
    private val deleteSetUseCase: DeleteSetUseCase,
    private val getExerciseListBySuperSetIdStreamUseCase: GetExerciseListBySuperSetIdStreamUseCase,
    private val approachListUseCase: GetSetListUseCase,
) : CoroutineViewModel() {

    val exerciseInfoLiveData = appSettings.idSuperSetFlow().flatMapLatest { superSetId ->
        getExerciseListBySuperSetIdStreamUseCase.invoke(superSetId).map { exerciseDomainList ->
            exerciseDomainList.map { exerciseDomain ->
                ViewHolderTypes.ExerciseInfo(
                    exercise = exerciseDomain.toModel(),
                    approaches = approachListUseCase.invoke(exerciseId = exerciseDomain.id).map { approachDomain ->
                        approachDomain.toModel()
                    }
                )
            }
        }
    }
        .asLiveData()

    val reoccurrencesLiveData = appSettings.getRepsStream().asLiveData()
    val weightLiveData = appSettings.getWeightStream().asLiveData()

    @ExperimentalCoroutinesApi
    val approachLiveData = getCurrentSetStreamUseCase.invoke().map { approachDomainList ->
        approachDomainList.map { approachDomain -> approachDomain.toModel() }
    }.asLiveData()

    fun exerciseInfoFirst(): ViewHolderTypes.ExerciseInfo {
        return runBlocking {
            return@runBlocking appSettings.idSuperSetFlow().flatMapLatest { superSetId ->
                getExerciseListBySuperSetIdStreamUseCase.invoke(superSetId).map { exerciseDomainList ->
                    exerciseDomainList.map { exerciseDomain ->
                        ViewHolderTypes.ExerciseInfo(
                            exercise = exerciseDomain.toModel(),
                            approaches = approachListUseCase.invoke(exerciseId = exerciseDomain.id)
                                .map { approachDomain ->
                                    approachDomain.toModel()
                                }
                        )
                    }
                }
            }.first()[0]
        }
    }

    fun deleteApproach(approach: Approach) {
        launch {
            deleteSetUseCase.invoke(set = approach.toDomain())
        }
    }

    fun rememberIdExercise(exercise: Exercise) {
        launch {
            appSettings.setIdExercise(exercise.id)
        }
    }

    fun addNewApproach(approach: Approach) {
        launch {
            saveSetUseCase.invoke(set = approach.toDomain())
        }
    }
}
