package com.yankin.trainingdiary.screen.season_ticket_info

import android.annotation.SuppressLint
import android.content.res.Resources
import androidx.lifecycle.asLiveData
import com.yankin.trainingdiary.R
import com.yankin.trainingdiary.datastore.AppSettings
import com.yankin.trainingdiary.support.CoroutineViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SeasonTicketInfoViewModel @Inject constructor(private val appSettings: AppSettings) :
    CoroutineViewModel() {

    val numberOfTrainingSessionLiveData = appSettings.numberOfTrainingSessionsFlow().asLiveData()

    val subscriptionEndDate = appSettings.subscriptionEndDateFlow().asLiveData()

    fun resetSeasonTicket() {
        launch {
            appSettings.setNumberOfTrainingSessions(-1)
            appSettings.setSubscriptionEndDate("")
            appSettings.setDateCreatedTicket("")
            appSettings.setDayLeft(-1)
        }
    }

    fun daysAmount(): String {
        return runBlocking {
            val dateEnd = dateFormatter.parse(appSettings.getSubscriptionEndDate())!!.time
            val currentDate = Date().time
            val result = dateEnd - currentDate
            return@runBlocking if (dayFormatter.format(result).equals("0")) {
                Resources.getSystem().getString(R.string.last_day)
            } else {
                if (dayFormatter.format(dateEnd).equals("1")) {
                    "ထ"
                } else {
                    dayFormatter.format(result)
                }
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
