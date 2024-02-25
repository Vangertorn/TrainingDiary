package com.yankin.approach_create.impl.presentation.state

import com.yankin.approach_create.impl.presentation.models.SetCreateStateModel
import com.yankin.exercise.api.models.ExerciseDomain
import com.yankin.exercise_pattern.api.models.ExercisePatternDomain
import com.yankin.set.api.models.SetDomain
import kotlinx.coroutines.flow.Flow

internal interface SetCreateStateOwner {

    val weight: Double
    val reps: Int
    val selectedExerciseId: Long?
    val exerciseName: String
    val exerciseComment: String?
    val exerciseNameByUser: String?

    fun getStateStream(): Flow<SetCreateStateModel>

    fun updateState(exercisePatternList: List<ExercisePatternDomain>)
    fun updateState(selectedExerciseId: Long)
    fun updateState(defaultWeight: Double, defaultReps: Int)
    fun updateState(setDomainList: List<SetDomain>, exerciseId: Long)
    fun updateState(exerciseDomain: ExerciseDomain)
    fun updateState(exerciseName: ExerciseName)
    fun updateState(exerciseComment: ExerciseComment)
    fun updateState(weight: Double)
    fun updateState(reps: Int)
}

@JvmInline
value class ExerciseName(
    val value: String
)

@JvmInline
value class ExerciseComment(
    val value: String
)