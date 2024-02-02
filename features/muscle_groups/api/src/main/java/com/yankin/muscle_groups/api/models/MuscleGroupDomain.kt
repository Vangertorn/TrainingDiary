package com.yankin.muscle_groups.api.models

data class MuscleGroupDomain(
    val id: Long,
    val nameMuscleGroup: String,
    val factorySettings: Boolean,
    val deleted: Boolean,
)
