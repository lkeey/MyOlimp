package com.developement.myolimp.feature_onborading.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.developement.myolimp.feature_onborading.screens.SplashScreen
import com.developement.myolimp.ui.theme.GradientBackground
import com.developement.myolimp.ui.theme.RoundLineBackground

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GradientBackground {
                RoundLineBackground {
                    SplashScreen()
                }
            }
        }
    }
}
