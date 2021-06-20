package com.example.trainingdiary

import android.app.Application
import com.example.trainingdiary.dao.DatabaseConstructor
import com.example.trainingdiary.dao.PlannerDatabase
import com.example.trainingdiary.datastore.AppSettings
import com.example.trainingdiary.repository.*
import com.example.trainingdiary.screen.approach_create.ApproachCreateViewModel
import com.example.trainingdiary.screen.exercise_autofill.ExerciseAutofillViewModel
import com.example.trainingdiary.screen.exercise_create.ExerciseCreateViewModel
import com.example.trainingdiary.screen.exercise_list.ExerciseListViewModel
import com.example.trainingdiary.screen.season_ticket.SeasonTicketViewModel
import com.example.trainingdiary.screen.season_ticket_info.SeasonTicketInfoViewModel
import com.example.trainingdiary.screen.settings.SettingsFragment
import com.example.trainingdiary.screen.settings.SettingsViewModel
import com.example.trainingdiary.screen.super_set_create.SuperSetCreateViewModel
import com.example.trainingdiary.screen.training_create.TrainingCreateViewModel
import com.example.trainingdiary.screen.training_list.TrainingListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module


class DiaryApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DiaryApp)
            modules(listOf(viewModel, barnModel, repositoryModel))
        }
    }

    private val viewModel = module {
        viewModel { TrainingListViewModel(get(), get()) }
        viewModel { TrainingCreateViewModel(get(), get(),get()) }
        viewModel { ApproachCreateViewModel(get(), get(), get(), get()) }
        viewModel { ExerciseCreateViewModel(get(), get(),get(),get()) }
        viewModel { ExerciseListViewModel(get(), get(), get()) }
        viewModel { MainActivityViewModel(get(), get(), get(),get()) }
        viewModel { SettingsViewModel(get(), get()) }
        viewModel { ExerciseAutofillViewModel(get()) }
        viewModel { SeasonTicketViewModel(get()) }
        viewModel { SeasonTicketInfoViewModel(get()) }
        viewModel { SuperSetCreateViewModel(get(),get(),get(),get(),get()) }
    }
    private val repositoryModel = module {
        factory { TrainingRepository(get(), get()) }
        factory { ApproachRepository(get(), get(), get()) }
        factory { ExerciseRepository(get(), get(), get()) }
        factory { MuscleGroupRepository(get()) }
        factory { ExerciseAutofillRepository(get()) }
        factory { SuperSetRepository(get(),get(),get(),get()) }
    }

    private val barnModel = module {
        single { DatabaseConstructor.create(get()) }
        factory { get<PlannerDatabase>().trainingDao() }
        factory { get<PlannerDatabase>().approachDao() }
        factory { get<PlannerDatabase>().exerciseDao() }
        factory { get<PlannerDatabase>().muscleGroupDao() }
        factory { get<PlannerDatabase>().exerciseAutofillDao() }
        factory { get<PlannerDatabase>().superSetDao() }
        single { AppSettings(get()) }
    }
}