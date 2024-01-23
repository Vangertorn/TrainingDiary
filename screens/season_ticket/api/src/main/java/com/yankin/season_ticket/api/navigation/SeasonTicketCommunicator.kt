package com.yankin.season_ticket.api.navigation

interface SeasonTicketCommunicator {

    fun navigateToSeasonTicket()

    fun navigateToSeasonTicketInfo()

    companion object {
        const val NAV_KEY = "SeasonTicketCommunicatorNavKey"
    }
}