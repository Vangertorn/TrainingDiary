package com.yankin.approach_create.impl.presentation.state

import com.yankin.set.api.models.SetDomain

internal data class ExerciseStateModel(
    val exerciseId: Long,
    val setList: List<SetDomain>,
    val exerciseName: String,
    val exerciseComment: String?,
    val weightCurrentSet: Double?,
    val repsCurrentSet: Int?,
    val exerciseNameByUser: String?,
    val exerciseCommentByUser: String?,
    val weightByUser: Double?,
    val repsByUser: Int?,
)