package com.yankin.exercise_list.impl.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import com.yankin.approach_create.api.navigation.ApproachCreateCommunicator
import com.yankin.approach_create.api.navigation.ApproachCreateParams
import com.yankin.common.fragment.SupportFragmentInset
import com.yankin.common.recyclerview.SwipeCallback
import com.yankin.exercise_create.api.navigation.ExerciseCreateCommunicator
import com.yankin.exercise_create.api.navigation.ExerciseCreateParams
import com.yankin.exercise_list.api.navigation.ExerciseListCommunicator
import com.yankin.exercise_list.impl.navigation.ExerciseListParcelableParams
import com.yankin.exercise_list.impl.presentation.models.ViewHolderTypes
import com.yankin.navigation.BundleParcelable
import com.yankin.training_create.api.navigation.TrainingCreateCommunicator
import com.yankin.training_create.api.navigation.TrainingCreateParams
import com.yankin.trainingdiary.exercise_list.impl.R
import com.yankin.trainingdiary.exercise_list.impl.databinding.FragmentExerciseListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@AndroidEntryPoint
class ExerciseListFragment : SupportFragmentInset<FragmentExerciseListBinding>(R.layout.fragment_exercise_list) {

    @Inject
    lateinit var exerciseCreateCommunicator: ExerciseCreateCommunicator

    @Inject
    lateinit var trainingCreateCommunicator: TrainingCreateCommunicator

    @Inject
    lateinit var approachCreateCommunicator: ApproachCreateCommunicator

    override lateinit var viewBinding: FragmentExerciseListBinding

    private val viewModel: ExerciseListViewModel by viewModels()
    private val params by BundleParcelable<ExerciseListParcelableParams>(ExerciseListCommunicator.NAV_KEY)

    @ExperimentalCoroutinesApi
    private val simpleCallback = SwipeCallback { position, direction ->
        when (direction) {
            ItemTouchHelper.LEFT -> {
                delete(position)
            }

            ItemTouchHelper.RIGHT -> {
                delete(position)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentExerciseListBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.fragment_exercise_list, container, false)
        )
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listLiveData.observe(this.viewLifecycleOwner) {
            val adapter = ExerciseRecyclerViewAdapter(
                it,
                onClick = { item ->
                    if (item is ViewHolderTypes.ExerciseInfo) {
                        viewModel.rememberIdExercise(item.exercise)
                        approachCreateCommunicator.navigateToApproachCreate(
                            ApproachCreateParams(
                                exerciseId = item.exercise.id,
                                name = item.exercise.name,
                                trainingId = item.exercise.idTraining ?: 0L,
                                position = item.exercise.position,
                                comment = item.exercise.comment,
                                deleted = item.exercise.deleted,
                                idSet = item.exercise.idSet
                            )
                        )
                    } else {
                        viewModel.rememberIdSuperSet((item as ViewHolderTypes.SuperSetDate).superSet)
                        approachCreateCommunicator.navigateToSuperSetApproachCreate()
                    }
                }
            )
            viewBinding.recyclerView.adapter = adapter
        }

        val exerciseHelper = ItemTouchHelper(simpleCallback)
        exerciseHelper.attachToRecyclerView(viewBinding.recyclerView)

        params.let { training ->
            viewBinding.commentExerciseList.text = training.comment
            viewBinding.dateTrainingExerciseList.text = training.date
            viewBinding.muscleGroupsExerciseList.text = training.muscleGroups
            viewBinding.weightExerciseList.text =
                if (!training.weight.isNullOrEmpty()) if (training.muscleGroups.isNullOrEmpty()) getString(
                    R.string.weight, training.weight
                ) else getString(R.string.weight1, training.weight) else ""
        }
        viewBinding.toolbarExerciseList.setNavigationOnClickListener {
            viewModel.forgotIdTraining()
            findNavController().popBackStack()
        }
        viewBinding.btnAdd.setOnClickListener {
            exerciseCreateCommunicator.navigateToExerciseCreate(ExerciseCreateParams(params.trainingId))
        }
        viewBinding.ivEditTrainingExerciseList.setOnClickListener {
            trainingCreateCommunicator.navigateTo(
                TrainingCreateParams.EditTraining(
                    trainingId = params.trainingId,
                    date = params.date,
                    muscleGroups = params.muscleGroups,
                    comment = params.comment,
                    weight = params.weight,
                    position = params.position,
                    deleted = params.deleted,
                    selectedMuscleGroup = params.selectedMuscleGroup,
                )
            )
        }
    }

    @ExperimentalCoroutinesApi
    private fun delete(position: Int) {
        if (viewModel.getExerciseFromPosition(position) is ViewHolderTypes.ExerciseInfo) {
            val exercise =
                viewModel.getExerciseFromPosition(position) as ViewHolderTypes.ExerciseInfo
            viewModel.deletedExerciseTrue(exercise.exercise)
            Snackbar.make(
                viewBinding.recyclerView,
                getString(R.string.exercise_was_delete),
                Snackbar.LENGTH_LONG
            )
                .setAction(getString(R.string.undo)) {
                    viewModel.deletedExerciseFalse(exercise.exercise)
                }.apply {
                    this.view.translationY = -savedInsets.bottom.toFloat()
                }.show()
        } else {
            val superSet =
                viewModel.getExerciseFromPosition(position) as ViewHolderTypes.SuperSetDate
            viewModel.deletedSuperSetTrue(superSet.superSet)
            Snackbar.make(
                viewBinding.recyclerView,
                getString(R.string.super_set_was_deleted),
                Snackbar.LENGTH_LONG
            )
                .setAction(getString(R.string.undo)) {
                    viewModel.deletedSuperSetFalse(superSet.superSet)
                }.apply {
                    this.view.translationY = -savedInsets.bottom.toFloat()
                }.show()
        }
    }

    override fun onInsetsReceived(top: Int, bottom: Int, hasKeyboard: Boolean) {
        viewBinding.toolbarExerciseList.setPadding(0, top, 0, 0)
    }
}
