package com.yankin.domain.api.model

data class MuscleGroupDomain(
    val id: Long = 0,
    val nameMuscleGroup: String,
    val factorySettings: Boolean,
    val deleted: Boolean = false
)
