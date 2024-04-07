package com.yankin.trainingdiary.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import com.yankin.api.AppThemeOwner
import com.yankin.api.models.ThemeModel
import com.yankin.common.activity.SupportActivityInset
import com.yankin.common.fragment.setWindowTransparency
import com.yankin.common.themes.currentTheme
import com.yankin.common.viewbinding.viewBinding
import com.yankin.navigation.NavigationNode
import com.yankin.training_list.impl.navigation.TrainingListNavigationNode
import com.yankin.trainingdiary.R
import com.yankin.trainingdiary.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : SupportActivityInset(), AppThemeOwner {

    override val theme: ThemeModel = ThemeModel.Light

    @Inject
    lateinit var navigationNodes: @JvmSuppressWildcards Set<NavigationNode>

    private val viewBinding by viewBinding(ActivityMainBinding::inflate)
    private val navHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment }
    private val navController: NavController by lazy { navHostFragment.navController }
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("onCreate", "Current screen: ${this::class.java.name}")
        setTheme(currentTheme)
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        setupNavGraph()
        viewModel.onDefaultMuscleGroupsLaunch()
        setWindowTransparency(this)
    }

    override fun getActiveFragment(): Fragment? {
        return (supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment)
            .childFragmentManager.fragments[0]
    }

    private fun setupNavGraph() {
        navController.graph = navController.createGraph(
            startDestination = TrainingListNavigationNode.ROUTE
        ) {
            navigationNodes.forEach { navNode ->
                navNode.addNode(this)
            }
        }
    }
}
