package com.yankin.training_list.impl.presentation.adapter

import com.yankin.common.recyclerview.adapter.BaseAsyncListDifferDelegationAdapter

internal class TrainingsAdapter(
    onTrainingClick: (trainingId: Long) -> Unit
) : BaseAsyncListDifferDelegationAdapter() {

    init {
        delegatesManager.addDelegate(trainingAdapterDelegate(onTrainingClick))
    }
}