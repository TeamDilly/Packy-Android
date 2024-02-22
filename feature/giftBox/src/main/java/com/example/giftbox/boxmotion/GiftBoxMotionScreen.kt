package com.example.giftbox.boxmotion

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.giftbox.navigation.GiftBoxRoute
import com.packy.core.common.BoxOpenLottie
import com.packy.domain.model.getbox.GiftBox
import kotlinx.coroutines.delay

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GiftBoxMotionScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    giftBox: GiftBox
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            BoxOpenLottie.entries[giftBox.box.id.toInt().minus(1)].openLottie
        )
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        speed = 1.3f
    )
    LaunchedEffect(progress) {
        if (progress == 1f) {
            navController.navigate(
                GiftBoxRoute.getGiftBoxDetailOpenFadeRoute(
                    giftBox,
                    false
                )
            ) {
                val currentRoute = navController.currentBackStackEntry?.destination?.route
                currentRoute?.let { popUpTo(it) { inclusive = true } }
            }
        }
    }
    Scaffold { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(16f / 35f)
            )
        }
    }
}