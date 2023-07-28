package com.developement.myolimp.feature_onborading.activities

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.developement.myolimp.R
import com.developement.myolimp.feature_onborading.models.FragmentImg
import com.developement.myolimp.feature_onborading.screens.OnBoardingScreen
import com.developement.myolimp.feature_onborading.view_models.OnBoardingViewModel
import com.developement.myolimp.ui.theme.MyOlimpTheme

class OnBoardingActivity : ComponentActivity() {

    private val viewModel: OnBoardingViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    /*
        Add all images and text
    */
        addFragments()

        setContent {
            MyOlimpTheme {
                OnBoardingScreen(viewModel = viewModel)
            }
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
