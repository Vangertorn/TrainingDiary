package com.example.trainingdiary

import android.app.Application
import com.example.trainingdiary.dao.DatabaseConstructor
import com.example.trainingdiary.dao.PlannerDatabase
import com.example.trainingdiary.datastore.AppSettings
import com.example.trainingdiary.repository.ApproachRepository
import com.example.trainingdiary.repository.ExerciseRepository
import com.example.trainingdiary.repository.MuscleGroupRepository
import com.example.trainingdiary.repository.TrainingRepository
import com.example.trainingdiary.screen.approach_create.ApproachCreateViewModel
import com.example.trainingdiary.screen.exercise_create.ExerciseCreateViewModel
import com.example.trainingdiary.screen.exercise_list.ExerciseListViewModel
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
        viewModel { TrainingListViewModel(get(), get(), get()) }
        viewModel { TrainingCreateViewModel(get(), get()) }
        viewModel { ApproachCreateViewModel(get()) }
        viewModel { ExerciseCreateViewModel(get()) }
        viewModel { ExerciseListViewModel(get(), get(), get()) }
    }
    private val repositoryModel = module {
        factory { TrainingRepository(get(), get(), get(), get()) }
        factory { ApproachRepository(get(), get(), get()) }
        factory { ExerciseRepository(get(), get(), get()) }
        factory { MuscleGroupRepository(get()) }
    }

    private val barnModel = module {
        single { DatabaseConstructor.create(get()) }
        factory { get<PlannerDatabase>().trainingDao() }
        factory { get<PlannerDatabase>().approachDao() }
        factory { get<PlannerDatabase>().exerciseDao() }
        factory { get<PlannerDatabase>().muscleGroupDao() }
        single { AppSettings(get()) }
    }
}