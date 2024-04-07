package com.yankin.trainingdiary.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yankin.common.resource_import.CommonRString
import com.yankin.coroutine.launchJob
import com.yankin.muscle_groups.api.models.MuscleGroupDomain
import com.yankin.muscle_groups.api.usecases.GetAllMuscleGroupListUseCase
import com.yankin.muscle_groups.api.usecases.SaveDefaultMuscleGroupListUseCase
import com.yankin.resource_manager.api.ResourceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val saveDefaultMuscleGroupListUseCase: SaveDefaultMuscleGroupListUseCase,
    private val getAllMuscleGroupListUseCase: GetAllMuscleGroupListUseCase,
    private val resourceManager: ResourceManager,
) : ViewModel() {

    fun onDefaultMuscleGroupsLaunch() {
        viewModelScope.launchJob(Throwable::printStackTrace) {
            getAllMuscleGroupListUseCase.invoke().ifEmpty {
                saveDefaultMuscleGroupListUseCase.invoke(
                    listOf(
                        MuscleGroupDomain(
                            nameMuscleGroup = resourceManager.getString(CommonRString.legs),
                            id = 0,
                            isDefault = true,
                            deleted = false
                        ),
                        MuscleGroupDomain(
                            nameMuscleGroup = resourceManager.getString(CommonRString.all_muscle_groups),
                            id = 0,
                            isDefault = true,
                            deleted = false
                        ),
                        MuscleGroupDomain(
                            nameMuscleGroup = resourceManager.getString(CommonRString.breast),
                            id = 0,
                            isDefault = true,
                            deleted = false
                        ),
                        MuscleGroupDomain(
                            nameMuscleGroup = resourceManager.getString(CommonRString.biceps),
                            id = 0,
                            isDefault = true,
                            deleted = false
                        ),
                        MuscleGroupDomain(
                            nameMuscleGroup = resourceManager.getString(CommonRString.shoulders),
                            id = 0,
                            isDefault = true,
                            deleted = false
                        ),
                        MuscleGroupDomain(
                            nameMuscleGroup = resourceManager.getString(CommonRString.back),
                            id = 0,
                            isDefault = true,
                            deleted = false
                        ),
                        MuscleGroupDomain(
                            nameMuscleGroup = resourceManager.getString(CommonRString.triceps),
                            id = 0,
                            isDefault = true,
                            deleted = false
                        )
                    )
                )
            }
        }
    }
}
