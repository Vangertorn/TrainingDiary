package com.yankin.approach_create.impl.presentation.approach_create

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yankin.approach_create.impl.presentation.Approach
import com.yankin.trainingdiary.approach_create.impl.R

class ApproachRecyclerViewAdapter(private val onClick: (Approach) -> Unit) :
    ListAdapter<Approach, ApproachRecyclerViewAdapter.ApproachViewHolder>(
        ApproachAdapterDiffCallBack()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ApproachViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_approach, parent, false),
        ::onItemClick
    )

    override fun onBindViewHolder(holder: ApproachViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun onItemClick(position: Int) {
        onClick(getItem(position))
    }

    inner class ApproachViewHolder(itemView: View, private val itemClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val tvText = itemView.findViewById<TextView>(R.id.tvApproach)

        init {
            itemView.setOnClickListener {
                itemClick(adapterPosition)
            }
        }

        fun bind(item: Approach) {
            tvText.text =
                itemView.context.getString(R.string.approach, item.weight, item.reoccurrences)
        }
    }

    class ApproachAdapterDiffCallBack : DiffUtil.ItemCallback<Approach>() {
        override fun areItemsTheSame(oldItem: Approach, newItem: Approach): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Approach, newItem: Approach): Boolean {
            return oldItem.reoccurrences == newItem.reoccurrences &&
                oldItem.weight == newItem.weight
        }
    }
}
