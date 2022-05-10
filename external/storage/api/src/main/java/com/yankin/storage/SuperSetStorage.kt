package com.yankin.storage

import com.yankin.models.SuperSetDomain
import com.yankin.models.info.SuperSetInfoDomain
import kotlinx.coroutines.flow.Flow

interface SuperSetStorage {

    fun getSuperSetInfoByTrainingIdAndFlagsFlow(
        id: Long,
        flags: Boolean,
        visibility: Boolean
    ): Flow<List<SuperSetInfoDomain>>

    fun getSuperSetById(idSuperSet: Long): SuperSetDomain

    fun updateSuperSet(superSetDomainEntity: SuperSetDomain)

    fun deletedSuperSetByVisible()

    fun insertSuperSet(superSetDomainEntity: SuperSetDomain): Long
}