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
import com.packy.createbox.navigation.CreateBoxScreens


@Composable
fun BoxShareMotionScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    lottieAnimation: String,
    createdBoxId: Long
) {

    fun moveNext() {
        // 여기서 createdBoxId 가 null 인 경우는 완전한 버그이다.
        navController.navigate(
            CreateBoxScreens.BoxShareFadeIn.create(createdBoxId)
        ) {
            val currentRoute = navController.currentBackStackEntry?.destination?.route
            currentRoute?.let { popUpTo(it) { inclusive = true } }
            launchSingleTop = true
        }
    }

    if (lottieAnimation.isBlank()) {
        moveNext()
    } else {
        Box(
            modifier = modifier
                .fillMaxSize(),
        ) {
            val composition by rememberLottieComposition(LottieCompositionSpec.Url(lottieAnimation))
            val anim = rememberLottieAnimatable()

            LaunchedEffect(composition) {
                anim.animate(
                    composition = composition,
                    speed = -1.3f,
                    initialProgress = 1f
                )
                if (anim.value < 0.1f) {
                    moveNext()
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
}