package com.yankin.approach_create.impl.presentation.state

import androidx.lifecycle.SavedStateHandle
import com.yankin.approach_create.impl.presentation.models.SetCreateStateModel
import com.yankin.exercise.api.models.ExerciseDomain
import com.yankin.exercise_pattern.api.models.ExercisePatternDomain
import com.yankin.set.api.models.SetDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

internal class SetCreateStateOwnerImpl(
    private val savedStateHandle: SavedStateHandle
) : SetCreateStateOwner {
    private val setCreateStateModel = MutableStateFlow(
        SetCreateStateModel(
            exercisePatternList = listOf(),
            exerciseStateModelList = savedStateHandle.get<Array<ExerciseStateModel>>(EXERCISE_STATE_MODEL_LIST_KEY)
                ?.toList() ?: emptyList(),
            selectedExerciseId = savedStateHandle.get<Long>(SELECTED_EXERCISE_ID_KEY),
            defaultWeight = 0.0,
            defaultReps = 0,
        )
    )
    override val weight: Double
        get() = setCreateStateModel.value.exerciseStateModelList.firstOrNull { exerciseStateModel ->
            exerciseStateModel.exerciseId == setCreateStateModel.value.selectedExerciseId
        }?.let { exerciseStateModel ->
            exerciseStateModel.weightByUser ?: exerciseStateModel.weightCurrentSet
        } ?: setCreateStateModel.value.defaultWeight
    override val reps: Int
        get() = setCreateStateModel.value.exerciseStateModelList.firstOrNull { exerciseStateModel ->
            exerciseStateModel.exerciseId == setCreateStateModel.value.selectedExerciseId
        }?.let { exerciseStateModel ->
            exerciseStateModel.repsByUser ?: exerciseStateModel.repsCurrentSet
        } ?: setCreateStateModel.value.defaultReps
    override val selectedExerciseId: Long?
        get() = setCreateStateModel.value.selectedExerciseId
    override val exerciseName: String by lazy {
        setCreateStateModel.value.exerciseStateModelList.firstOrNull { exerciseStateModel ->
            exerciseStateModel.exerciseId == setCreateStateModel.value.selectedExerciseId
        }?.exerciseName ?: throw EmptyExerciseNameException()
    }
    override val exerciseNameByUser: String?
        get() {
            val name = setCreateStateModel.value.exerciseStateModelList.firstOrNull { exerciseStateModel ->
                exerciseStateModel.exerciseId == setCreateStateModel.value.selectedExerciseId
            }?.exerciseNameByUser
            return name
        }
    override val exerciseComment: String?
        get(){
            val comment = setCreateStateModel.value.exerciseStateModelList.firstOrNull { exerciseStateModel ->
                exerciseStateModel.exerciseId == setCreateStateModel.value.selectedExerciseId
            }?.let { exerciseStateModel ->
                exerciseStateModel.exerciseCommentByUser ?: exerciseStateModel.exerciseComment
            }
            return comment
        }

    override fun getStateStream(): Flow<SetCreateStateModel> = setCreateStateModel

    override fun updateState(exercisePatternList: List<ExercisePatternDomain>) {
        setCreateStateModel.update { stateModel ->
            stateModel.copy(exercisePatternList = exercisePatternList)
        }
    }

    override fun updateState(selectedExerciseId: Long) {
        setCreateStateModel.update { stateModel ->
            stateModel.copy(selectedExerciseId = selectedExerciseId).also { updatedStateModel ->
                savedStateHandle[SELECTED_EXERCISE_ID_KEY] = updatedStateModel.selectedExerciseId
            }
        }
    }

    override fun updateState(defaultWeight: Double, defaultReps: Int) {
        setCreateStateModel.update { stateModel ->
            stateModel.copy(defaultWeight = defaultWeight, defaultReps = defaultReps)
        }
    }

    override fun updateState(setDomainList: List<SetDomain>, exerciseId: Long) {
        setCreateStateModel.update { stateModel ->
            val exerciseStateModel = stateModel.exerciseStateModelList.firstOrNull { exerciseStateModel ->
                exerciseStateModel.exerciseId == exerciseId
            } ?: return
            val updatedExerciseStateModel = exerciseStateModel.copy(
                setList = setDomainList,
                weightCurrentSet = setDomainList.lastOrNull()?.weight,
                repsCurrentSet = setDomainList.lastOrNull()?.reps
            )
            val index = stateModel.exerciseStateModelList.indexOf(exerciseStateModel)
            val updatedList = stateModel.exerciseStateModelList.toMutableList()
            updatedList[index] = updatedExerciseStateModel
            stateModel.copy(exerciseStateModelList = updatedList)
        }
    }

    override fun updateState(exerciseDomain: ExerciseDomain) {
        setCreateStateModel.update { stateModel ->
            val exerciseStateModel = stateModel.exerciseStateModelList.firstOrNull { exerciseStateModel ->
                exerciseStateModel.exerciseId == exerciseDomain.id
            }
            if (exerciseStateModel == null) {
                val newExerciseStateModel = ExerciseStateModel(
                    exerciseId = exerciseDomain.id,
                    setList = listOf(),
                    exerciseName = exerciseDomain.name,
                    exerciseComment = exerciseDomain.comment,
                    weightCurrentSet = null,
                    repsCurrentSet = null,
                    exerciseNameByUser = savedStateHandle[EXERCISE_NAME_KEY + exerciseDomain.id],
                    exerciseCommentByUser = savedStateHandle[EXERCISE_COMMENT_KEY + exerciseDomain.id],
                    weightByUser = savedStateHandle[SET_WEIGHT_KEY + exerciseDomain.id],
                    repsByUser = savedStateHandle[SET_REPS_KEY + exerciseDomain.id],
                )
                val updatedList = stateModel.exerciseStateModelList.toMutableList()
                updatedList.add(newExerciseStateModel)
                stateModel.copy(exerciseStateModelList = updatedList)
            } else {
                val updatedExerciseStateModel = exerciseStateModel.copy(
                    exerciseName = exerciseDomain.name,
                    exerciseComment = exerciseDomain.comment,
                )
                val index = stateModel.exerciseStateModelList.indexOf(exerciseStateModel)
                val updatedList = stateModel.exerciseStateModelList.toMutableList()
                updatedList[index] = updatedExerciseStateModel
                stateModel.copy(exerciseStateModelList = updatedList)
            }
        }
    }

    override fun updateState(exerciseName: ExerciseName) {
        setCreateStateModel.update { stateModel ->
            val exerciseId = setCreateStateModel.value.selectedExerciseId
            val exerciseStateModel = stateModel.exerciseStateModelList.firstOrNull { exerciseStateModel ->
                exerciseStateModel.exerciseId == exerciseId
            } ?: return
            if (exerciseStateModel.exerciseName == exerciseName.value) return
            val updatedExerciseStateModel = exerciseStateModel.copy(
                exerciseNameByUser = exerciseName.value,
            ).also {
                savedStateHandle[EXERCISE_NAME_KEY + exerciseId] = exerciseName.value
            }
            val index = stateModel.exerciseStateModelList.indexOf(exerciseStateModel)
            val updatedList = stateModel.exerciseStateModelList.toMutableList()
            updatedList[index] = updatedExerciseStateModel
            stateModel.copy(exerciseStateModelList = updatedList)
        }
    }

    override fun updateState(exerciseComment: ExerciseComment) {
        setCreateStateModel.update { stateModel ->
            val exerciseId = setCreateStateModel.value.selectedExerciseId
            val exerciseStateModel = stateModel.exerciseStateModelList.firstOrNull { exerciseStateModel ->
                exerciseStateModel.exerciseId == exerciseId
            } ?: return
            if (exerciseStateModel.exerciseComment == exerciseComment.value) return
            val updatedExerciseStateModel = exerciseStateModel.copy(
                exerciseCommentByUser = exerciseComment.value,
            ).also {
                savedStateHandle[EXERCISE_COMMENT_KEY + exerciseId] = exerciseComment.value
            }
            val index = stateModel.exerciseStateModelList.indexOf(exerciseStateModel)
            val updatedList = stateModel.exerciseStateModelList.toMutableList()
            updatedList[index] = updatedExerciseStateModel
            stateModel.copy(exerciseStateModelList = updatedList)
        }
    }

    override fun updateState(weight: Double) {
        setCreateStateModel.update { stateModel ->
            val exerciseId = setCreateStateModel.value.selectedExerciseId
            val exerciseStateModel = stateModel.exerciseStateModelList.firstOrNull { exerciseStateModel ->
                exerciseStateModel.exerciseId == exerciseId
            } ?: return
            if (exerciseStateModel.weightCurrentSet == weight || stateModel.defaultWeight == weight) return
            val updatedExerciseStateModel = exerciseStateModel.copy(
                weightCurrentSet = weight,
            ).also {
                savedStateHandle[SET_WEIGHT_KEY + exerciseId] = weight
            }
            val index = stateModel.exerciseStateModelList.indexOf(exerciseStateModel)
            val updatedList = stateModel.exerciseStateModelList.toMutableList()
            updatedList[index] = updatedExerciseStateModel
            stateModel.copy(exerciseStateModelList = updatedList)
        }
    }

    override fun updateState(reps: Int) {
        setCreateStateModel.update { stateModel ->
            val exerciseId = setCreateStateModel.value.selectedExerciseId
            val exerciseStateModel = stateModel.exerciseStateModelList.firstOrNull { exerciseStateModel ->
                exerciseStateModel.exerciseId == exerciseId
            } ?: return
            if (exerciseStateModel.repsCurrentSet == reps || stateModel.defaultReps == reps) return
            val updatedExerciseStateModel = exerciseStateModel.copy(
                repsCurrentSet = reps,
            ).also {
                savedStateHandle[SET_REPS_KEY + exerciseId] = reps
            }
            val index = stateModel.exerciseStateModelList.indexOf(exerciseStateModel)
            val updatedList = stateModel.exerciseStateModelList.toMutableList()
            updatedList[index] = updatedExerciseStateModel
            stateModel.copy(exerciseStateModelList = updatedList)
        }
    }

    companion object {
        private const val EXERCISE_STATE_MODEL_LIST_KEY = "EXERCISE_STATE_MODEL_LIST_KEY"
        private const val SELECTED_EXERCISE_ID_KEY = "SELECTED_EXERCISE_ID_KEY"
        private const val EXERCISE_COMMENT_KEY = "EXERCISE_COMMENT_KEY"
        private const val EXERCISE_NAME_KEY = "EXERCISE_NAME_KEY"
        private const val SET_WEIGHT_KEY = "SET_WEIGHT_KEY"
        private const val SET_REPS_KEY = "SET_REPS_KEY"
    }
}