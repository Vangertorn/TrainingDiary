package com.yankin.trainingdiary.screen.exercise_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yankin.trainingdiary.R
import com.yankin.trainingdiary.models.info.ViewHolderTypes

class ExerciseRecyclerViewAdapter(
    private val list: List<ViewHolderTypes>,
    private val onClick: (ViewHolderTypes) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            ExerciseViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_exercise_list, parent, false),
                ::onItemClick
            )
        } else {
            SuperSetViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_super_set, parent, false),
                ::onItemClick
            )
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == 0) {
            (holder as ExerciseViewHolder).bind(list[position] as ViewHolderTypes.ExerciseInfo)
        } else {
            (holder as SuperSetViewHolder).bind(list[position] as ViewHolderTypes.SuperSetDate)
        }
    }

    private fun onItemClick(position: Int) {
        onClick(list[position])
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is ViewHolderTypes.ExerciseInfo -> 0
            is ViewHolderTypes.SuperSetDate -> 1
        }
    }

    inner class ExerciseViewHolder(itemView: View, private val itemClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val tvExerciseName =
            itemView.findViewById<TextView>(R.id.tvNameExercise_exercise_item)
        private val tvComment =
            itemView.findViewById<TextView>(R.id.tvCommentExercise_exercise_item)
        private val rvApproach = itemView.findViewById<RecyclerView>(R.id.rvApproachInExerciseItem)
        //TODO не получилось обновить либу без мавен репозитория
//        private val chipsLayoutManager =
//            ChipsLayoutManager.newBuilder(itemView.context).setChildGravity(Gravity.TOP)
//                .setScrollingEnabled(false)
//                .setGravityResolver { Gravity.CENTER }
//                .setRowBreaker { adapterPosition == 6 }
//                .setOrientation(ChipsLayoutManager.HORIZONTAL)
//                .setRowStrategy(ChipsLayoutManager.STRATEGY_FILL_VIEW)
//                .withLastRow(true)
//                .build()
        private val adapter = ApproachRecyclerViewAdapterInExerciseItem()

        init {
            itemView.setOnClickListener {
                itemClick(adapterPosition)
            }
//            rvApproach.layoutManager = chipsLayoutManager
            rvApproach.adapter = adapter
        }

        fun bind(item: ViewHolderTypes.ExerciseInfo) {

            tvExerciseName.text = item.exercise.name
            tvComment.text = item.exercise.comment
            adapter.submitList(item.approaches)
        }
    }

    inner class SuperSetViewHolder(itemView: View, private val itemClick: (Int) -> Unit) :

        RecyclerView.ViewHolder(itemView) {

        private val rvExerciseInfo =
            itemView.findViewById<RecyclerView>(R.id.rvExerciseInfoInSuperSet)
        private val adapter = ExerciseRecyclerViewAdapterInSuperSet()

        init {
            itemView.setOnClickListener {
                itemClick(adapterPosition)
            }

            rvExerciseInfo.adapter = adapter
        }

        fun bind(item: ViewHolderTypes.SuperSetDate) {
            adapter.submitList(item.exercise)
        }
    }
}
