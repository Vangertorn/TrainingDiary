package com.example.trainingdiary

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.trainingdiary.databinding.ActivityMainBinding
import androidx.fragment.app.Fragment
import com.example.myapplication.support.SupportActivityInset
import com.example.myapplication.support.setWindowTransparency

class MainActivity : SupportActivityInset<ActivityMainBinding>() {

    override lateinit var viewBinding: ActivityMainBinding
    private val navHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment }
    private val navController: NavController by lazy { navHostFragment.navController }
    private val handler = Handler(Looper.getMainLooper())
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setWindowTransparency(this)
    }

    override fun onBackPressed() {
        if (navHostFragment.childFragmentManager.backStackEntryCount == 0) {
            if (doubleBackToExitPressedOnce) {
                finish()
                return
            }
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Press \"back\" again to exit", Toast.LENGTH_SHORT).show()
            handler.postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
        } else navController.popBackStack()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    override fun getActiveFragment(): Fragment? {
        return navHostFragment.childFragmentManager.fragments[0]
    }
}



//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setContentView(R.layout.activity_main)
//        val button = findViewById<Button>(R.id.button)
//        val root_layout = findViewById<LinearLayout>(R.id.root_layout)
//        button.setOnClickListener {
//            // Initialize a new CardView instance
//            val cardView = CardView(this)
//
//            // Initialize a new LayoutParams instance, CardView width and height
//            val layoutParams = LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT, // CardView width
//                LinearLayout.LayoutParams.WRAP_CONTENT // CardView height
//            )
//
//            // Set margins for card view
//            layoutParams.setMargins(20, 20, 20, 20)
//
//            // Set the card view layout params
//            cardView.layoutParams = layoutParams
//
//            // Set the card view corner radius
//            cardView.radius = 16F
//
//            // Set the card view content padding
//            cardView.setContentPadding(25,25,25,25)
//
//            // Set the card view background color
//            cardView.setCardBackgroundColor(Color.LTGRAY)
//
//            // Set card view elevation
//            cardView.cardElevation = 8F
//
//            // Set card view maximum elevation
//            cardView.maxCardElevation = 12F
//
//            // Set a click listener for card view
//            cardView.setOnClickListener{
//                Toast.makeText(
//                    applicationContext,
//                    "Card clicked.",
//                    Toast.LENGTH_SHORT).show()
//            }
//
//            // Add an ImageView to the CardView
////            cardView.addView(generateImageView())
//
//            // Finally, add the CardView in root layout
//            root_layout.addView(cardView)
//        }
//    }
