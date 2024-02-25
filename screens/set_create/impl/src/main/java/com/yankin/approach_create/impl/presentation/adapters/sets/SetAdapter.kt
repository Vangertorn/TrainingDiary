package com.yankin.approach_create.impl.presentation.adapters.sets

import com.yankin.common.recyclerview.adapter.BaseAsyncListDifferDelegationAdapter

internal class SetAdapter(
    setClickListener: (setId: Long) -> Unit,
) : BaseAsyncListDifferDelegationAdapter() {

    init {
        delegatesManager.addDelegate(setAdapterDelegate(setClickListener))
    }
}