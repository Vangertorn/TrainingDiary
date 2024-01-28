package com.yankin.trainingdiary.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import com.yankin.api.AppThemeOwner
import com.yankin.api.models.ThemeModel
import com.yankin.common.activity.SupportActivityInset
import com.yankin.common.fragment.setWindowTransparency
import com.yankin.common.resource_import.CommonRString
import com.yankin.common.themes.currentTheme
import com.yankin.navigation.NavigationNode
import com.yankin.training_list.impl.navigation.TrainingListNavigationNode
import com.yankin.trainingdiary.R
import com.yankin.trainingdiary.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : SupportActivityInset<ActivityMainBinding>(), AppThemeOwner {

    override val theme: ThemeModel = ThemeModel.Light

    @Inject
    lateinit var navigationNodes: @JvmSuppressWildcards Set<NavigationNode>

    override lateinit var viewBinding: ActivityMainBinding
    private val navHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment }
    private val navController: NavController by lazy { navHostFragment.navController }
    private val handler = Handler(Looper.getMainLooper())
    private var doubleBackToExitPressedOnce = false
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("onCreate", "Current screen: ${this::class.java.name}")
        setTheme(currentTheme)
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setupNavGraph()

        setWindowTransparency(this)
        viewModel.deletedTrainings()
        viewModel.deletedExercises()
        viewModel.deletedSuperSets()
        viewModel.setLeftDays()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (navHostFragment.childFragmentManager.backStackEntryCount == 0) {
            if (doubleBackToExitPressedOnce) {
                finish()
                return
            }

            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, getString(CommonRString.press_back), Toast.LENGTH_SHORT).show()
            handler.postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        } else navController.popBackStack()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    override fun getActiveFragment(): Fragment? {
        return (supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment).childFragmentManager.fragments[0]
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
