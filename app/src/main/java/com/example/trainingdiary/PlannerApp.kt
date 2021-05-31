package com.example.trainingdiary

import android.app.Application
import com.example.trainingdiary.dao.DatabaseConstructor
import com.example.trainingdiary.dao.PlannerDatabase
import com.example.trainingdiary.repository.ApproachRepository
import com.example.trainingdiary.repository.TrainingRepository
import com.example.trainingdiary.screen.approach_create.ApproachCreateViewModel
import com.example.trainingdiary.screen.training_create.TrainingCreateViewModel
import com.example.trainingdiary.screen.training_list.TrainingListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module


class PlannerApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PlannerApp)
            modules(listOf(viewModel, barnModel, repositoryModel))
        }
    }

    private val viewModel = module {
        viewModel { TrainingListViewModel(get()) }
        viewModel { TrainingCreateViewModel(get()) }
        viewModel { ApproachCreateViewModel(get()) }
    }
    private val repositoryModel = module {
        factory { TrainingRepository(get()) }
        factory { ApproachRepository(get()) }
    }

    private val barnModel = module {
        single { DatabaseConstructor.create(get()) }
        factory { get<PlannerDatabase>().trainingDao() }
        factory { get<PlannerDatabase>().approachDao() }
    }
}