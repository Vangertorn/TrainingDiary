package com.yankin.models.info

import com.yankin.models.ApproachDomain
import com.yankin.models.ExerciseDomain
import com.yankin.models.SuperSetDomain

sealed class ViewHolderTypesDomain(val viewType: Int) {

    class SuperSetDateDomain(
        val superSetDomain: SuperSetDomain,
        val exerciseDomain: List<ExerciseInfoDomain>?
    ) : ViewHolderTypesDomain(0)

    class ExerciseInfoDomain(
        val exerciseDomain: ExerciseDomain,
        val approachDomains: List<ApproachDomain>?
    ) : ViewHolderTypesDomain(1)
}
