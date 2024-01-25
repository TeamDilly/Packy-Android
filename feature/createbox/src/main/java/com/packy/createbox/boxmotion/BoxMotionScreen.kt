package com.packy.createbox.boxmotion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.packy.createbox.navigation.CreateBoxRoute
import kotlinx.coroutines.delay

@Composable
fun BoxMotionScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {

    LaunchedEffect(null) {
        delay(1000)
        navController.navigate(CreateBoxRoute.BOX_GUIDE_FADE_IN) {
            popUpTo(CreateBoxRoute.BOX_MOTION) { inclusive = true }
        }
    }
}