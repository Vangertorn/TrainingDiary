package com.yankin.trainingdiary.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yankin.common.resource_import.CommonRString
import com.yankin.muscle_groups.api.usecases.SaveDefaultMuscleGroupListUseCase
import com.yankin.training.api.usecases.ClearTrainingDeleteQueueUseCase
import com.yankin.training_block.api.usecases.ClearTrainingBlockDeleteQueueUseCase
import com.yankin.trainingdiary.presentation.models.MuscleGroup
import com.yankin.trainingdiary.presentation.models.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val saveDefaultMuscleGroupListUseCase: SaveDefaultMuscleGroupListUseCase,
    @ApplicationContext private val context: Context,
    private val clearTrainingDeleteQueueUseCase: ClearTrainingDeleteQueueUseCase,
    private val clearTrainingBlockDeleteQueueUseCase: ClearTrainingBlockDeleteQueueUseCase,
) : ViewModel() {

    fun deletedTrainings() {
        viewModelScope.launch {
            clearTrainingDeleteQueueUseCase.invoke()
        }
    }

    fun deletedExercises() {
        viewModelScope.launch {
            clearTrainingBlockDeleteQueueUseCase.invoke()
        }
    }

    init {
        viewModelScope.launch {
            saveDefaultMuscleGroupListUseCase.invoke(
                listOf(
                    MuscleGroup(nameMuscleGroup = context.getString(CommonRString.legs), factorySettings = true),
                    MuscleGroup(
                        nameMuscleGroup = context.getString(CommonRString.all_muscle_groups),
                        factorySettings = true
                    ),
                    MuscleGroup(nameMuscleGroup = context.getString(CommonRString.breast), factorySettings = true),
                    MuscleGroup(nameMuscleGroup = context.getString(CommonRString.biceps), factorySettings = true),
                    MuscleGroup(
                        nameMuscleGroup = context.getString(CommonRString.shoulders),
                        factorySettings = true
                    ),
                    MuscleGroup(nameMuscleGroup = context.getString(CommonRString.back), factorySettings = true),
                    MuscleGroup(nameMuscleGroup = context.getString(CommonRString.triceps), factorySettings = true)
                ).map { it.toDomain() }
            )
        }
    }
}
