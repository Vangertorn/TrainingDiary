package com.yankin.exercise.api.models

data class ExerciseDomain(
    val id: Long = 0,
    val name: String,
    val idTraining: Long? = null,
    val position: Int = 0,
    val comment: String? = null,
    val deleted: Boolean = false,
    val idSet: Long? = null,
)
