package com.developement.myolimp.feature_onborading.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.developement.myolimp.R
import com.developement.myolimp.feature_onborading.activities.MainActivity
import kotlinx.coroutines.delay

@Composable
fun SplashScreen() {

    val context = LocalContext.current
    val intent = Intent(context, MainActivity::class.java)

    LaunchedEffect(
        key1 = true
    ) {
        delay(3000L)
    /*
        After delay launch MainActivity
    */
        context.startActivity(intent)
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 64.dp),
            painter = painterResource(R.drawable.splash_screen_name),
            contentDescription = "splash screen name"
        )
    }
}
