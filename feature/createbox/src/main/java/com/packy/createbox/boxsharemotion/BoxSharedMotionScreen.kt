package com.packy.createbox.boxsharemotion

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.packy.core.common.BoxOpenLottie
import com.packy.createbox.navigation.CreateBoxRoute

@Composable
fun BoxShareMotionScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    boxId: Int,
) {

    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        val lottieAnimation = BoxOpenLottie.entries.getOrNull(boxId - 1) ?: BoxOpenLottie.BOX_OPEN_1
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottieAnimation.lottie))
        val anim = rememberLottieAnimatable()

        LaunchedEffect(composition) {
            anim.animate(
                composition = composition,
                speed = -1.3f,
                initialProgress = 1f
            )
            if (anim.value < 0.1f) {
                navController.navigate(CreateBoxRoute.BOX_SHARE_FADE_IN) {
                    val currentRoute = navController.currentBackStackEntry?.destination?.route
                    currentRoute?.let { popUpTo(it) { inclusive = true } }
                    launchSingleTop = true
                }
            }
        }

        LottieAnimation(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            composition = composition,
            progress = { anim.value },
        )
    }
}