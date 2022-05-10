package com.yankin.storage

import com.yankin.models.ApproachDomain

interface ApproachStorage {

    suspend fun insertApproach(approachDomain: ApproachDomain)

    suspend fun deleteApproach(approachDomain: ApproachDomain)
}