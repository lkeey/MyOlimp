package ramble.sokol.myolimp.feature_splash_onBoarding.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import ramble.sokol.myolimp.R
import ramble.sokol.myolimp.destinations.BeginAuthenticationScreenDestination
import ramble.sokol.myolimp.feature_splash_onBoarding.domain.models.FragmentImg
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.components.FilledBtn
import ramble.sokol.myolimp.feature_splash_onBoarding.presentation.components.FragmentImage
import ramble.sokol.myolimp.ui.theme.GreyDark
import ramble.sokol.myolimp.ui.theme.OlimpTheme
import ramble.sokol.myolimp.ui.theme.Transparent

@SuppressLint("SuspiciousIndentation", "UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun OnBoardingScreen(
    navigator: DestinationsNavigator
) {
    OlimpTheme (
        onReload = {},
        content = {
            val items = getFragments()
            val pagerState = rememberPagerState(
                initialPage = 0,
                initialPageOffsetFraction = 0f
            ) {
                items.size
            }
            val coroutineScope = rememberCoroutineScope()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Transparent),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = CenterHorizontally
            ) {
                HorizontalPager(
                    modifier = Modifier
                        .fillMaxWidth(),
                    state = pagerState,
                    pageContent = {
                        FragmentImage(
                            items = items,
                            position = it,
                            pagerState = pagerState
                        )
                    }
                )

                Spacer(modifier = Modifier.height(22.dp))

                FilledBtn(
                    text = stringResource(R.string.next)
                ) {
                    if (pagerState.currentPage + 1 < items.size) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(
                                pagerState.currentPage + 1,

                                )
                        }
                    } else {
                        navigator.popBackStack()

                        navigator.navigate(BeginAuthenticationScreenDestination)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier
                        .clickable {
                            navigator.popBackStack()

                            navigator.navigate(BeginAuthenticationScreenDestination)
                        },
                    text = stringResource(R.string.skip),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.regular)),
                        fontWeight = FontWeight(500),
                        color = if (pagerState.currentPage != 3) GreyDark else Color.Transparent,
                        letterSpacing = 0.3.sp,
                    )
                )
            }
        }
    )
}

@Composable
private fun getFragments() : List<FragmentImg> {
/*
    Add all images with text
*/
    val items = mutableListOf<FragmentImg>()

    items.add(
        FragmentImg(
            img = R.drawable.onboarding_1st,
            textTitle = stringResource(R.string.text_title_1_onboarding),
            textContent = stringResource(R.string.text_content_1_onboarding)
        )
    )

    items.add(
        FragmentImg(
            img = R.drawable.onboarding_2nd,
            textTitle = stringResource(R.string.text_title_2_onboarding),
            textContent = stringResource(R.string.text_content_2_onboarding)
        )
    )

    items.add(
        FragmentImg(
            img = R.drawable.onboarding_3rd,
            textTitle = stringResource(R.string.text_title_3_onboarding),
            textContent = stringResource(R.string.text_content_3_onboarding)
        )
    )

    items.add(
        FragmentImg(
            img = R.drawable.onboarding_4th,
            textTitle = stringResource(R.string.text_title_4_onboarding),
            textContent = stringResource(R.string.text_content_4_onboarding)
        )
    )

    return items
}
