package com.yankin.season_ticket.impl.navigation

import androidx.navigation.NavController
import com.yankin.navigation.navigateToDestination
import com.yankin.season_ticket.api.navigation.SeasonTicketCommunicator
import com.yankin.season_ticket.impl.navigation.SeasonTicketNavigationNode.Companion.SEASON_TICKET_DIALOG_DESTINATION
import com.yankin.season_ticket.impl.navigation.SeasonTicketNavigationNode.Companion.SEASON_TICKET_INFO_DIALOG_DESTINATION
import javax.inject.Inject

class SeasonTicketCommunicatorImpl @Inject constructor(
    private val navController: NavController
) : SeasonTicketCommunicator {

    override fun navigateToSeasonTicket() {
        navController.navigateToDestination(
            destinationRoute = SEASON_TICKET_DIALOG_DESTINATION,
            args = null,
        )
    }

    override fun navigateToSeasonTicketInfo() {
        navController.navigateToDestination(
            destinationRoute = SEASON_TICKET_INFO_DIALOG_DESTINATION,
            args = null,
        )
    }
}