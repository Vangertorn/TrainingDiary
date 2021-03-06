package com.example.trainingdiary.screen.training_list

import android.annotation.SuppressLint
import androidx.lifecycle.asLiveData
import com.example.trainingdiary.datastore.AppSettings
import com.example.trainingdiary.models.Training
import com.example.trainingdiary.repository.TrainingRepository
import com.example.trainingdiary.support.CoroutineViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*

class TrainingListViewModel(
    private val trainingRepository: TrainingRepository,
    private val appSettings: AppSettings,
) : CoroutineViewModel() {
    val trainingAscLiveData = trainingRepository.currentTrainingAscFlow.asLiveData()
    val trainingDescLiveData = trainingRepository.currentTrainingDescFlow.asLiveData()
    val switchOrderLiveData = appSettings.orderAddedFlow().asLiveData()
    val numberTrainingLiveData = appSettings.numberOfTrainingSessionsFlow().asLiveData()
    val numberLeftDaysLiveData = appSettings.leftDaysFlow().asLiveData()

    fun deletedTrainingTrue(training: Training) {

        launch {
            if (appSettings.getDateCreatedTicket().isNotEmpty()) {
                if (monthFormatter.parse(training.date)!! >= monthFormatter.parse(appSettings.getDateCreatedTicket())) {
                    appSettings.setNumberOfTrainingSessions(appSettings.getNumberOfTrainingSessions() + 1)
                }
            }

            trainingRepository.deletedTrainingTrue(training)
        }
    }

    fun getTrainingFromPosition(position: Int): Training? {
        return runBlocking {
            if (appSettings.orderAdded()) {
                return@runBlocking trainingAscLiveData.value?.get(position)
            } else {
                return@runBlocking trainingDescLiveData.value?.get(position)
            }
        }

    }

    fun numberOfTrainingSessions(): Int {
        return runBlocking {
            return@runBlocking appSettings.getNumberOfTrainingSessions()
        }
    }

    fun subscriptionEndDate(): String {
        return runBlocking {
            return@runBlocking appSettings.getSubscriptionEndDate()
        }
    }

    fun deletedTrainingFalse(training: Training) {

        launch {
            if (appSettings.getDateCreatedTicket().isNotEmpty()) {
                if (monthFormatter.parse(training.date)!!
                    >= monthFormatter.parse(appSettings.getDateCreatedTicket())
                ) {
                    appSettings.setNumberOfTrainingSessions(appSettings.getNumberOfTrainingSessions() - 1)
                }
            }

            trainingRepository.deletedTrainingFalse(training)

        }
    }

    fun rememberIdTraining(training: Training) {
        launch {
            appSettings.setIdTraining(training.id)
        }
    }

    companion object {
        @SuppressLint("ConstantLocale")
        val monthFormatter = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
    }

}