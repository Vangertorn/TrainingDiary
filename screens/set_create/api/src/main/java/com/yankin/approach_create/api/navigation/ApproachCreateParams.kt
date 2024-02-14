package com.yankin.approach_create.api.navigation

data class ApproachCreateParams(
    val exerciseId: Long,
    val name: String,
    val trainingId: Long,
    val position: Int,
    val comment: String?,
    val deleted: Boolean,
    val idSet: Long?,
)