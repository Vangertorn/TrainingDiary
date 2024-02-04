package com.yankin.membership.api.navigation

interface MembershipCommunicator {

    fun navigateToCreateMembership()

    fun navigateToMembership(params: MembershipParams)

    companion object {
        const val NAV_KEY = "SeasonTicketCommunicatorNavKey"
    }
}