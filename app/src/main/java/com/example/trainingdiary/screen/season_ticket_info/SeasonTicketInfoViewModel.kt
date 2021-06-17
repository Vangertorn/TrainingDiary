package com.example.trainingdiary.screen.season_ticket_info

import androidx.lifecycle.asLiveData
import com.example.trainingdiary.datastore.AppSettings
import com.example.trainingdiary.support.CoroutineViewModel
import kotlinx.coroutines.launch

class SeasonTicketInfoViewModel(private val appSettings: AppSettings) : CoroutineViewModel() {

    val numberOfTrainingSessionLiveData = appSettings.numberOfTrainingSessionsFlow().asLiveData()

    val subscriptionEndDate = appSettings.subscriptionEndDateFlow().asLiveData()

    fun resetSeasonTicket() {
        launch {
            appSettings.setNumberOfTrainingSessions(-1)
            appSettings.setSubscriptionEndDate("")
            appSettings.setDateCreatedTicket("")
        }
    }
}