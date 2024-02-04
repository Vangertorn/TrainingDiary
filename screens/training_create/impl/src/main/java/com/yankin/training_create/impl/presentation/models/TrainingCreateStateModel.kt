package com.yankin.training_create.impl.presentation.models

import com.yankin.membership.api.models.MembershipDomain
import com.yankin.muscle_groups.api.models.MuscleGroupDomain
import com.yankin.training.api.models.TrainingDomain

internal data class TrainingCreateStateModel(
    val muscleGroupList: List<MuscleGroupDomain>,
    val selectedMuscleGroupIdList: List<Int>,
    val currentTraining: TrainingDomain?,
    val selectedDate: String,
    val weight: String,
    val comment: String,
    val membership: MembershipDomain?
)