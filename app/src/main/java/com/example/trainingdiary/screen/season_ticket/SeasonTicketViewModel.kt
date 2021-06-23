package com.example.trainingdiary.screen.season_ticket

import android.annotation.SuppressLint
import com.example.trainingdiary.datastore.AppSettings
import com.example.trainingdiary.support.CoroutineViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class SeasonTicketViewModel(private val appSettings: AppSettings) : CoroutineViewModel() {

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

    fun saveDaysAmount(endDate: String) {
        launch {

            val dateEnd = dateFormatter.parse(endDate)!!.time
            if (dateEnd == 0L) {
                appSettings.setDayLeft(365)
            } else {
                val currentDate = Date().time
                val result = dateEnd - currentDate
                appSettings.setDayLeft(dayFormatter.format(result).toInt())
            }
        }
    }

    companion object {
        @SuppressLint("ConstantLocale")
        private val dateFormatter = SimpleDateFormat("dd.MM.yy", Locale.getDefault())

        @SuppressLint("ConstantLocale")
        private val dayFormatter = SimpleDateFormat("d", Locale.getDefault())
    }
}