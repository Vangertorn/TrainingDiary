package com.example.trainingdiary.screen.season_ticket

import com.example.trainingdiary.datastore.AppSettings
import com.example.trainingdiary.support.CoroutineViewModel
import kotlinx.coroutines.launch

class SeasonTicketViewModel(private val appSettings: AppSettings): CoroutineViewModel() {

    fun saveNumberOfTrainingSessions(number: Int) {
        launch {
            appSettings.setNumberOfTrainingSessions(number)
        }
    }

    fun saveSubscriptionEndDate(date: String) {
        launch {
            appSettings.setSubscriptionEndDate(date)
        }
    }

    fun saveDateCreatedTicket(date: String) {
        launch {
            appSettings.setDateCreatedTicket(date)
        }
    }
}