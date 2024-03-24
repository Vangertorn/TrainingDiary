package com.yankin.training_create.impl.presentation.models

import com.yankin.date.Timestamp
import com.yankin.membership.api.models.MembershipDomain
import com.yankin.muscle_groups.api.models.MuscleGroupDomain
import com.yankin.training.api.models.TrainingDomain

internal data class TrainingCreateStateModel(
    val muscleGroupList: List<MuscleGroupDomain>,
    val selectedMuscleGroupIdList: List<Long>,
    val currentTraining: TrainingDomain?,
    val selectedDate: Timestamp.Milliseconds,
    val weight: Double?,
    val comment: String,
    val membership: MembershipDomain?
) {
    fun getSelectedMuscleGroup(): List<MuscleGroupDomain> {
        return muscleGroupList.filter { muscleGroup ->
            muscleGroup.id in selectedMuscleGroupIdList
        }
    }
}