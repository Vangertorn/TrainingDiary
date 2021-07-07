package com.example.trainingdiary.screen.exercise_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.trainingdiary.datastore.AppSettings
import com.example.trainingdiary.models.Exercise
import com.example.trainingdiary.models.SuperSet
import com.example.trainingdiary.models.info.ViewHolderTypes
import com.example.trainingdiary.repository.ExerciseRepository
import com.example.trainingdiary.repository.SuperSetRepository
import com.example.trainingdiary.repository.TrainingRepository
import com.example.trainingdiary.support.CoroutineViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class ExerciseListViewModel(
    private val exerciseRepository: ExerciseRepository,
    private val trainingRepository: TrainingRepository,
    private val appSettings: AppSettings,
    private val superSetRepository: SuperSetRepository
) :
    CoroutineViewModel() {


    @ExperimentalCoroutinesApi
    private val exerciseInfoFlow = exerciseRepository.currentExerciseFlow
    private val superSetDateFlow = superSetRepository.currentSuperSetDateFlow

    @ExperimentalCoroutinesApi
    val listLiveData: LiveData<List<ViewHolderTypes>> =
        combine(exerciseInfoFlow, superSetDateFlow) { exerciseInfo, superSetDao ->

            exerciseInfo +
                    superSetDao

        }.asLiveData()

    @ExperimentalCoroutinesApi
    fun getExerciseFromPosition(position: Int): ViewHolderTypes {
        return listLiveData.value?.get(position)!!
    }

    fun deletedExerciseTrue(exercise: Exercise) {
        launch {
            exerciseRepository.deletedExerciseTrue(exercise)
        }
    }

    fun deletedExerciseFalse(exercise: Exercise) {
        launch {
            exerciseRepository.deletedExerciseFalse(exercise)
        }
    }

    fun deletedSuperSetTrue(superSet: SuperSet) {
        launch {
           superSetRepository.deletedSuperSetTrue(superSet)
        }
    }
    fun deletedSuperSetFalse(superSet: SuperSet) {
        launch {
            superSetRepository.deletedSuperSetFalse(superSet)
        }
    }

    fun forgotIdTraining() {
        launch {
            trainingRepository.forgotIdTraining()
        }
    }


    fun rememberIdExercise(exercise: Exercise) {
        launch {
            appSettings.setIdExercise(exercise.id)
        }
    }

    fun rememberIdSuperSet(superSet: SuperSet) {
        launch {
            appSettings.setSuperSetId(superSet.id)
        }
    }

    fun switchExercisePosition(exercise1: Exercise, exercise2: Exercise) {
        launch {
            exerciseRepository.switchExercisePosition(exercise1, exercise2)
        }
    }


}