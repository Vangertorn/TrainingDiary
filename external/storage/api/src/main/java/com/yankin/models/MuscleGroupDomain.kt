package com.yankin.models

data class MuscleGroupDomain(
    val id: Long = 0,
    val nameMuscleGroup: String,
    val factorySettings: Boolean,
    val deleted: Boolean = false
)
