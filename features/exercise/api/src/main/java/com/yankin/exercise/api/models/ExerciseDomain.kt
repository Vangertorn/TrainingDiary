package com.yankin.exercise.api.models

data class ExerciseDomain(
    val id: Long = 0,
    val name: String,
    val trainingBlockId: Long,
    val position: Int,
    val comment: String?,
)
