package com.yankin.approach.api.models

data class ApproachDomain(
    val id: Long = 0,
    val weight: String = "0",
    val repeat: String = "0",
    val idExercise: Long
)
