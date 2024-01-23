package com.yankin.season_ticket.impl.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.fragment.dialog
import com.yankin.navigation.NavigationNode
import com.yankin.season_ticket.impl.presentation.season_ticket.SeasonTicketBottomDialog
import com.yankin.season_ticket.impl.presentation.season_ticket_info.SeasonTicketInfoBottomDialog
import javax.inject.Inject

class SeasonTicketNavigationNode @Inject constructor() : NavigationNode {

    override fun addNode(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.apply {
            dialog<SeasonTicketBottomDialog>(SEASON_TICKET_DIALOG_DESTINATION)
            dialog<SeasonTicketInfoBottomDialog>(SEASON_TICKET_INFO_DIALOG_DESTINATION)
        }
    }

    companion object {
        const val SEASON_TICKET_DIALOG_DESTINATION = "SEASON_TICKET_DIALOG_DESTINATION"
        const val SEASON_TICKET_INFO_DIALOG_DESTINATION = "SEASON_TICKET_INFO_DIALOG_DESTINATION"
    }
}