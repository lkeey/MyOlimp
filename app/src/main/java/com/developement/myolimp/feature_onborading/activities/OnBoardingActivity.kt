package com.developement.myolimp.feature_onborading.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.developement.myolimp.R
import com.developement.myolimp.feature_onborading.models.FragmentImg
import com.developement.myolimp.feature_onborading.screens.OnBoardingScreen
import com.developement.myolimp.feature_onborading.view_models.OnBoardingViewModel
import com.developement.myolimp.ui.theme.BlueStart
import com.developement.myolimp.ui.theme.MyOlimpTheme
import com.developement.myolimp.ui.theme.Transparent
import com.developement.myolimp.ui.theme.White

class OnBoardingActivity : ComponentActivity() {

    private val viewModel: OnBoardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Transparent.toArgb()
        window.navigationBarColor = White.toArgb()

    /*
        Add all images and text
    */
        addFragments()

        setContent {
            OnBoardingScreen(viewModel = viewModel)
        }
    }

    private fun addFragments() {

        viewModel.deleteAllItems()

        viewModel.addItem(
            FragmentImg(
                img = R.drawable.onboarding_1st,
                textTitle = getString(R.string.text_title_1_onboarding),
                textContent = getString(R.string.text_content_1_onboarding)
            )
        )

        viewModel.addItem(
            FragmentImg(
                img = R.drawable.onboarding_2nd,
                textTitle = getString(R.string.text_title_2_onboarding),
                textContent = getString(R.string.text_content_2_onboarding)
            )
        )

        viewModel.addItem(
            FragmentImg(
                img = R.drawable.onboarding_3rd,
                textTitle = getString(R.string.text_title_3_onboarding),
                textContent = getString(R.string.text_content_3_onboarding)
            )
        )

        viewModel.addItem(
            FragmentImg(
                img = R.drawable.onboarding_4th,
                textTitle = getString(R.string.text_title_4_onboarding),
                textContent = getString(R.string.text_content_4_onboarding)
            )
        )
    }
}
