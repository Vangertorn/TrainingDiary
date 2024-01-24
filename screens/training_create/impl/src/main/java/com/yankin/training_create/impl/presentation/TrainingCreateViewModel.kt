package com.yankin.training_create.impl.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.asLiveData
import com.yankin.common.viewmodel.CoroutineViewModel
import com.yankin.muscle_groups.api.usecases.GetAllMuscleGroupListUseCase
import com.yankin.muscle_groups.api.usecases.GetCurrentMuscleGroupStreamUseCase
import com.yankin.preferences.AppSettings
import com.yankin.training.api.usecases.SaveTrainingUseCase
import com.yankin.training.api.usecases.UpdateTrainingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TrainingCreateViewModel @Inject constructor(
    private val getAllMuscleGroupListUseCase: GetAllMuscleGroupListUseCase,
    getCurrentMuscleGroupStreamUseCase: GetCurrentMuscleGroupStreamUseCase,
    private val saveTrainingUseCase: SaveTrainingUseCase,
    private val updateTrainingUseCase: UpdateTrainingUseCase,
    private val appSettings: AppSettings
) :
    CoroutineViewModel() {

    val muscleGroupLiveData = getCurrentMuscleGroupStreamUseCase.invoke().asLiveData()

    fun addNewTraining(training: Training) {
        launch {
            saveTrainingUseCase.invoke(training.toDomain())
        }
    }

    fun updateTraining(training: Training) {
        launch {
            updateTrainingUseCase.invoke(training.toDomain())
        }
    }

    fun addMuscleGroups(selectedPositions: List<Int>): String {
        val stringBuilder = StringBuilder()
        val listMuscleGroups = runBlocking {
            getAllMuscleGroupListUseCase.invoke()
        }
        for (index in listMuscleGroups.indices) {
            if (index in selectedPositions) {
                stringBuilder.append(listMuscleGroups[index].nameMuscleGroup)
                stringBuilder.append(", ")
            }
        }
        return stringBuilder.toString().removeSuffix(", ")
    }

    fun takeAwayTraining(training: Training) {
        launch {
            val numberTraining = appSettings.getNumberOfTrainingSessions()
            if (numberTraining < 100) {
                if (appSettings.getDateCreatedTicket().isNotEmpty()) {
                    if (monthFormatter.parse(training.date)!!
                        >= monthFormatter.parse(
                            appSettings.getDateCreatedTicket()
                        )
                    ) {
                        if (numberTraining == 0) {
                            appSettings.setNumberOfTrainingSessions(-1)
                            appSettings.setSubscriptionEndDate("")
                            appSettings.setDateCreatedTicket("")
                            appSettings.setDayLeft(-1)
                        } else {
                            appSettings.setNumberOfTrainingSessions(numberTraining - 1)
                        }
                    }
                }
            }
        }
    }

    companion object {
        @SuppressLint("ConstantLocale")
        val monthFormatter = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
    }
}
