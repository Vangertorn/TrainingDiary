package com.yankin.membership.impl.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class MembershipParcelableParams(
    val membershipId: Long,
) : Parcelable