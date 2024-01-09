package com.yankin.super_set.api.models

data class SuperSetDomain(
    val id: Long = 0,
    val idTraining: Long,
    val deleted: Boolean = false,
    val visibility: Boolean = false,
    val position: Int = 0
)
