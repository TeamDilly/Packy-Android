package com.packy.createbox.boxmotion

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.packy.core.theme.PackyTheme
import com.packy.core.values.Strings
import com.packy.core.widget.animation.TextAnimation
import com.packy.createbox.navigation.CreateBoxScreens

@Composable
fun BoxMotionScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    lottieAnimation: String,
) {

    val (textAlpha, textOffsetY) = TextAnimation()

    fun moveNext() {
        navController.navigate(CreateBoxScreens.BoxGuideFadeIn.name) {
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
            val progress by animateLottieCompositionAsState(
                composition,
                isPlaying = true,
                speed = 1.4f
            )

            LottieAnimation(
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(16f / 35f)
                    .align(Alignment.Center),
                composition = composition,
                progress = { progress },
            )

            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 104.dp)
                    .offset(y = textOffsetY)
                    .alpha(textAlpha),
                text = Strings.CREATE_BOX_MOTION_TITLE,
                style = PackyTheme.typography.heading01.copy(
                    textAlign = TextAlign.Center
                ),
                color = PackyTheme.color.gray900,
            )
            if (progress == 1f) {
                moveNext()
            }
        }
    }
}